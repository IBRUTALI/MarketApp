package com.ighorosipov.marketapp.presentation.login

import android.graphics.Rect
import android.view.View
import android.view.View.OnFocusChangeListener
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import androidx.core.widget.doAfterTextChanged
import androidx.navigation.fragment.findNavController
import com.ighorosipov.marketapp.R
import com.ighorosipov.marketapp.databinding.FragmentLoginBinding
import com.ighorosipov.marketapp.utils.base.BaseFragment
import com.ighorosipov.marketapp.utils.di.appComponent
import com.ighorosipov.marketapp.utils.di.lazyViewModel
import ru.tinkoff.decoro.MaskImpl
import ru.tinkoff.decoro.parser.UnderscoreDigitSlotsParser
import ru.tinkoff.decoro.watchers.FormatWatcher
import ru.tinkoff.decoro.watchers.MaskFormatWatcher


class LoginFragment : BaseFragment<FragmentLoginBinding, LoginViewModel>(
    FragmentLoginBinding::inflate
) {
    override val viewModel: LoginViewModel by lazyViewModel {
        requireContext().appComponent().loginViewModel().create()
    }

    private val globalLayoutListener = OnGlobalLayoutListener {
        // on below line we are creating a variable for rect
        val rect = Rect()
        // on below line getting frame for our relative layout.
        binding.loginLayout.getWindowVisibleDisplayFrame(rect)
        // on below line getting screen height for relative layout.
        val screenHeight = binding.loginLayout.rootView.height
        // on below line getting keypad height.
        val keypadHeight = screenHeight - rect.bottom
        // on below line we are checking if keypad height is greater than screen height.
        if (keypadHeight > screenHeight * 0.15) {
            // displaying toast message as keyboard showing.
            binding.conditionsGroup.visibility = View.GONE
        } else {
            // displaying toast message as keyboard closed.
            binding.conditionsGroup.visibility = View.VISIBLE
        }
    }

    override fun inject() {
        requireContext().appComponent().inject(this)
    }

    override fun initViews() {
        addKeyboardListener()
        validateEditFields()
        clearEditFieldsVisibility()
        loginButton()
    }

    private fun loginButton() {
        binding.loginButton.setOnClickListener {
            viewModel.insertUser()
            binding.loginLayout.viewTreeObserver.removeOnGlobalLayoutListener(globalLayoutListener)
            findNavController().navigate(R.id.action_loginFragment_to_tabsFragment)
        }
    }

    private fun addKeyboardListener() {
        binding.loginLayout.viewTreeObserver.addOnGlobalLayoutListener(globalLayoutListener)
    }

    private fun validateEditFields() {
        with(binding) {
            val slots = UnderscoreDigitSlotsParser().parseSlots("+7 ___ ___ __ __")
            val formatWatcher: FormatWatcher = MaskFormatWatcher(MaskImpl.createTerminated(slots))
            numberInputLayout.editText?.let { formatWatcher.installOn(it) }
            firstnameInputLayout.editText?.doAfterTextChanged {
                firstnameInputLayout.apply {
                    error = null
                }
                viewModel.setFirstnameChanges(it?.toString() ?: "")
            }
            lastnameInputLayout.editText?.doAfterTextChanged {
                lastnameInputLayout.apply {
                    error = null
                }
                viewModel.setLastnameChanges(it?.toString() ?: "")
            }
            numberInputLayout.editText?.doAfterTextChanged {
                numberInputLayout.apply {
                    error = null
                }
                viewModel.setNumberChanges(it?.toString() ?: "")
            }
        }
    }

    private fun clearEditFieldsVisibility() {
        binding.apply {
            firstnameInputLayout.editText?.onFocusChangeListener =
                OnFocusChangeListener { _, hasFocus ->
                    if (!hasFocus && firstnameInputLayout.editText?.text?.isNotBlank() == true) {
                        firstnameInputLayout.isEndIconVisible = true
                    }
                }
            lastnameInputLayout.editText?.onFocusChangeListener =
                OnFocusChangeListener { _, hasFocus ->
                    if (!hasFocus && lastnameInputLayout.editText?.text?.isNotBlank() == true) {
                        lastnameInputLayout.isEndIconVisible = true
                    }
                }
            numberInputLayout.editText?.onFocusChangeListener =
                OnFocusChangeListener { _, hasFocus ->
                    if (!hasFocus && numberInputLayout.editText?.text?.isNotBlank() == true) {
                        numberInputLayout.isEndIconVisible = true
                    }
                }
        }
    }

    override fun subscribeToObservers() {
//        viewModel.startUserValues.observe(viewLifecycleOwner) { user ->
//            binding.apply {
//                firstnameInputLayout.editText?.setText(user?.firstname, TextView.BufferType.EDITABLE)
//                lastnameInputLayout.editText?.setText(user?.lastname, TextView.BufferType.EDITABLE)
//                numberInputLayout.editText?.setText(user?.number, TextView.BufferType.EDITABLE)
//            }
//        }
        viewModel.inputErrors.observe(viewLifecycleOwner) { list ->
            binding.apply {
                loginButton.isEnabled = list.isNullOrEmpty()
                list.forEach {
                    when (it) {
                        UserInputError.InvalidFirstname -> {
                            firstnameInputLayout.error = getString(R.string.invalid_char)
                        }

                        UserInputError.InvalidLastname -> {
                            lastnameInputLayout.error = getString(R.string.invalid_char)
                        }

                        UserInputError.InvalidNumber -> {
                            numberInputLayout.error = ""
                        }

                        UserInputError.EmptyFields -> {
                            loginButton.isEnabled = viewModel.isFieldsNotEmpty(
                                firstname = firstnameInputLayout.editText?.text.toString(),
                                lastname = lastnameInputLayout.editText?.text.toString(),
                                number = numberInputLayout.editText?.text.toString()
                            ) && list.isNullOrEmpty()
                        }
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.loginLayout.viewTreeObserver.removeOnGlobalLayoutListener(globalLayoutListener)
    }

}

