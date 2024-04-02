# :department_store: MarketApp

MarketApp - это приложение для онлайн-магазина.  
Приложение позволяет пользователям регистрироваться в магазине локально. Пользователи могут фильтровать/сортировать товары по нужным категориям.

# Обзор приложения:

## 1 . 💻 Экран регистрации  

  📍 На экране регистрации все поля проходят валидацию.  
  📍 Поля имени и фамилии считаются валидными, если содержат только кириллицу.   
  📍 Поле номера считается валидным, если содержит 11 цифр.  
  📍 Кнопка регистрации неактивна, пока невалидно хотя бы одно поле.  
  📍 Данные сохраняются в локальную базу данных.   
  📍 После успешной регистрации пользователя, экран регистрации больше не появляется, пока пользователь не выйдет из аккаунта.  
  
<p align="center">
  <img align="center" width="30%" src="https://github.com/IBRUTALI/MarketApp/assets/96013243/bd71dbd2-7098-491e-9f78-2bc55d3feea0">
  <img align="center" width="30%" src="https://github.com/IBRUTALI/MarketApp/assets/96013243/0fe56e3d-023b-46df-966a-e58424acb458">
  <img align="center" width="30%" src="https://github.com/IBRUTALI/MarketApp/assets/96013243/029be614-ad9f-44b1-bdea-4be80fe27af3">
</p>
  
## 2 . 🔍 Экран "Каталог"  

📍 Здесь отображается список товаров.  
📍 Возможность добавить товар в избранное.  
📍 Пользователь может смотреть изображения товара не кликая на него.  
📍 Сортировка списка и фильтрация по тегам.  

<p align="center">
  <img align="center" width="30%" src="https://github.com/IBRUTALI/MarketApp/assets/96013243/f1a4b17a-a4b5-4638-b8e7-2305f6bf6498">
  <img align="center" width="30%" src="https://github.com/IBRUTALI/MarketApp/assets/96013243/11091a35-45e5-40aa-9e9b-7e1612a5f306">
</p>

## 3 . 💵 Экран "Товар"

  <img align="left" width="30%" src="https://github.com/IBRUTALI/MarketApp/assets/96013243/6fe15cbb-a05c-4066-a74f-62e35896cb17"> 

 ➡️ Здесь отображается подробное описание выбранного товара.  
 ➡️ Возможность добавить товар в избранное.  
 ➡️ Пользователь может смотреть изображения товара.  
 ➡️ Возможность cкрыть/показать описание или ингредиенты.
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>

  ## 4 . 😐 Экран "Профиль"

  <img align="right" width="30%" src="https://github.com/IBRUTALI/MarketApp/assets/96013243/caf63510-4d28-4fd6-9853-623ef6cd9faa">
  
 ➡️ Здесь отображается профиль пользователя.  
 ➡️ Возможность посмотреть избранные товары.  
 ➡️ Возможность выйти из аккаунта.  
 ➡️ При выходе из аккаунта пользователя выкидывает на экран регистрации.
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>

  ## 5 . ♥️ Экран "Избранное"

 <img align="left" width="30%" src="https://github.com/IBRUTALI/MarketApp/assets/96013243/b32e6a7b-0fc2-42cb-a099-51daa1947169">

 ➡️ Здесь отображается список избранных товаров.  
 ➡️ Возможность удалить товар из избранного.  
 ➡️ Действия добавления/удаления избранного синхронизированы между всеми экранами.  
 ➡️ При клике на товар открывается экран с подробным описание товара.
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
  
## 🛠️ Технологии:
- Kotlin.
- Верстка обычная на XML.
- Для хранения и кэширования данных используется Room.
- Для навигации Jetpack Navigation.
- DI - Dagger2.
- Архитектура - MVVM.
- Clean Architecture.
- Custom View.
- LiveData.
- Coroutines.
