package sample.junit;

import sample.AutorizationData;
import sample.Database.DatabaseHandler;
import sample.SerializeClasses.*;

import java.io.*;
import java.net.Socket;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ClientHandler implements Runnable {

    DatabaseHandler dbHandler = new DatabaseHandler();

    private static Socket clientDialog;

    public ClientHandler(Socket client) {
        ClientHandler.clientDialog = client;
    }

    @Override
    public void run() {
        try {
            // инициируем каналы общения в сокете, для сервера
            ObjectInputStream in = new ObjectInputStream(clientDialog.getInputStream());
            System.out.println("DataInputStream создан");
            ObjectOutputStream out = new ObjectOutputStream(clientDialog.getOutputStream());
            System.out.println("DataOutputStream  создан");
            ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            // основная рабочая часть //
            //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            // начинаем диалог с подключенным клиентом в цикле, пока сокет не
            // закрыт клиентом
            while (!clientDialog.isClosed()) {
                System.out.println("Чтение сервера с канала");
                Object  obj = in.readObject();

                //АВТОРИЗАЦИЯ
                if(obj instanceof AutorizationData)
                {
                    boolean resAutorization;
                    String status;
                    int resSatus = 0;
                    AutorizationData autorazationData = (AutorizationData) obj;
                    System.out.println("Принятые данные для авторизации: ");
                    System.out.println("  Логин: " + autorazationData.getLogin()+"\n  Пароль: "+ autorazationData.getPassword());

                    ResultSet result = dbHandler.Autorization(autorazationData.getLogin(), autorazationData.getPassword());
                    int counter = 0;
                    while(result.next()){
                        counter++;
                    }
                    if(counter >= 1){
                        resAutorization = true;
                        status = dbHandler.AutorizationStatus(autorazationData.getLogin());
                        if(status.equals("admin")) {
                            System.out.println("Авторизация в статусе: АДМИНИСТРАТОР");
                            resSatus = 1;
                        } else{
                            System.out.println("Авторизация в статусе: ПОЛЬЗОВАТЕЛЬ");
                            resSatus = 2;
                            dbHandler.AutorizationBlackList(autorazationData.getLogin());
                        }
                    }else{
                        resAutorization = false;
                    }
                    System.out.println("Результат авторизации: " + resAutorization);
                    out.writeBoolean(resAutorization);
                    out.writeInt(resSatus);
                }

                if(obj instanceof BlackListProverka)
                {
                    BlackListProverka data = (BlackListProverka) obj;
                    System.out.println("Принятые данные для проверки ЧС: ");
                    System.out.println("  Логин пользователя: " + data.getLogin());
                    Boolean result = dbHandler.AutorizationBlackList(data.getLogin());
                    System.out.println("Черный список: " + result);
                    out.writeBoolean(result);
                }

                //ГИСТОГРАММА
                if(obj instanceof DataForGistogram)
                {
                    DataForGistogram data = (DataForGistogram) obj;
                    System.out.println("Запрос данных для построения гистограммы по результатам расчетов прибыли от продаж...");
                    out.writeObject(dbHandler.DataGistogram());
                }

                if(obj instanceof DataForAnalis)
                {
                    DataForAnalis data = (DataForAnalis) obj;
                    System.out.println("Запрос данных для построения гистограммы по количеству продаж...");
                    out.writeObject(dbHandler.DataAnalis());
                }

                //РЕГИСТРАЦИЯ
                if(obj instanceof RegistrationData)
                {
                    boolean resRegistration;

                    RegistrationData registrationData = (RegistrationData) obj;
                    ResultSet result = dbHandler.RegistrationProv(registrationData.getLogin());
                    System.out.println("Принятые данные для регистрации: ");
                    System.out.println("  Имя: " + registrationData.getUserName()+"\n  Фамилия: "+ registrationData.getUserSurname()
                            +"\n  Телефон: "+ registrationData.getTelephoneNumber()+"\n  E-mail: "+ registrationData.getEmail()
                            +"\n  Логин: "+ registrationData.getLogin()+"\n  Пароль: "+ registrationData.getPassword());
                    int counter = 0;
                    while(result.next()){
                        counter++;
                    }
                    if(counter >= 1){
                        resRegistration = false;
                    }else{
                        resRegistration = true;

                        dbHandler.Registration(registrationData.getUserName(), registrationData.getUserSurname(),
                                registrationData.getEmail(), registrationData.getTelephoneNumber(), registrationData.getLogin(),
                                registrationData.getPassword());
                    }
                    System.out.println("Результат регистрации: " + resRegistration);
                    out.writeBoolean(resRegistration);
                }

                //ДОБАВЛЕНИЕ В ЧЕРНЫЙ СПИСОК
                if(obj instanceof BlackListData)
                {
                    BlackListData blackListData = (BlackListData) obj;
                    System.out.println("Добавление пользователя " + blackListData.getLogin() + " в черный список... ");
                    Boolean result = dbHandler.BlackList(blackListData.getLogin());
                }

                //УДАЛЕНИЕ ИЗ ЧЕРНОГО СПИСКА
                if(obj instanceof BlackListDelData)
                {
                    BlackListDelData blackListData = (BlackListDelData) obj;
                    System.out.println("Удаление пользователя " + blackListData.getLogin() + " из черного... ");
                    Boolean result = dbHandler.BlackListDel(blackListData.getLogin());
                }

                //ЗАКАЗ ТОВАРА
                if(obj instanceof BuyData)
                {
                    BuyData buyData = (BuyData) obj;
                    System.out.println("Покупка товара №" +  buyData.getId() + "...");
                    Boolean result = dbHandler.BuyList(buyData.getId());
                    System.out.println("Заказ товара: " + result);
                    out.writeBoolean(result);
                }

                //ДОБАВЛЕНИЕ НОВОГО ТОВАРА
                if(obj instanceof AddNewProductData)
                {
                    AddNewProductData addNewProductData = (AddNewProductData) obj;
                        System.out.println("Принятые данные для добавления нового товара в базу: ");
                        System.out.println("  Категория: " + addNewProductData.getCategories()+"\n  Наименование: "+ addNewProductData.getProductName()
                                +"\n  Краткое описание: "+ addNewProductData.getProductDescription()+"\n  Материал: "+ addNewProductData.getMaterial()
                                +"\n  Цвет: "+addNewProductData.getColor()+"\n  Страна производитель: "+ addNewProductData.getCountry()
                                +"\n  Стоимость: "+ addNewProductData.getPrice() + "\n  Количество товара: "+ addNewProductData.getKlSklad()+ "\n  Дополнительные характеристики: "+ addNewProductData.getInformation()
                                +"\n  Себестоимость единицы продукции: "+ addNewProductData.getGrassProfit() +"\n  Комерческие расходы: "+ addNewProductData.getSellingExpenses()
                                +"\n  Управленческие расходы: "+ addNewProductData.getManagementExpenses());

                    dbHandler.AddNewProduct(addNewProductData.getCategories(), addNewProductData.getProductName(), addNewProductData.getProductDescription(),
                            addNewProductData.getMaterial(), addNewProductData.getColor(), addNewProductData.getCountry(), Double.parseDouble(addNewProductData.getPrice()),
                            Integer.parseInt(addNewProductData.getKlSklad()), addNewProductData.getInformation(), Double.parseDouble(addNewProductData.getGrassProfit()), Double.parseDouble(addNewProductData.getSellingExpenses()),
                            Double.parseDouble(addNewProductData.getManagementExpenses()));
               }


                // ПРОСМОТР СПИСКА ТОВАРОВ
                if(obj instanceof ShowInrormationAboutProductData)
                {
                    ShowInrormationAboutProductData showData = (ShowInrormationAboutProductData) obj;
                    System.out.println("Запрос списка продукции...");
                    out.writeObject(dbHandler.ShowProduct());
                }

                // ПРОСМОТР СПИСКА ТОВАРОВ ПО КАТЕГОРИЯМ
                if(obj instanceof ShowInformationForCategory)
                {
                    ShowInformationForCategory showData = (ShowInformationForCategory) obj;
                    System.out.println("Запрос информации о товарах категории " + showData.getCategory() + "...");
                     out.writeObject(dbHandler.ShowCategory(showData.getCategory()));
                }

                // ПРОСМОТР СПИСКА ПОЛЬЗОВАТЕЛЕЙ
                if(obj instanceof ShowClientData)
                {
                    ShowClientData showUsers = (ShowClientData) obj;
                    System.out.println("Запрос списка пользователей...");
                    out.writeObject(dbHandler.ShowUsers());
                }

                // ПРОСМОТР СПИСКА РЕЗУЛЬТАТОВ
                if(obj instanceof ShowResultProfit)
                {
                    ShowResultProfit showData = (ShowResultProfit) obj;
                    System.out.println("Запрос списка результатов расчета прибыли...");
                    out.writeObject(dbHandler.ShowResult());
                }

                // РАСЧЕТ ПРИБЫЛИ ОТ ПРОДАЖИ ВСЕЙ ПРОДУКЦИИ
                if(obj instanceof ProfitAllData)
                {
                    ProfitAllData profitData = (ProfitAllData) obj;
                    System.out.println("Запрос расчета прибыли от продаж всей продукции салона...");
                    double result = dbHandler.ProfitAll();
                    out.writeDouble(result);
                }

                // РАСЧЕТ ПРИБЫЛИ ОТ ПРОДАЖИ ПО ПЕРВОЙ КАТЕГОРИИ ПРОДУКЦИИ
                if(obj instanceof ProfitCategory1)
                {
                    ProfitCategory1 profitData = (ProfitCategory1) obj;
                    System.out.println("Запрос расчета прибыли от продаж по " + profitData.getCategory() + " категории товаров...");
                    double result = dbHandler.ProfitCategory1(profitData.getCategory());
                    out.writeDouble(result);
                }

                // РАСЧЕТ ПРИБЫЛИ ОТ ПРОДАЖИ ОПРЕДЕЛЕННОГО ТОВАРА
                if(obj instanceof ProfitOneData)
                {
                    ProfitOneData profitData = (ProfitOneData) obj;
                    System.out.println("Запрос расчета прибыли от продаж " + profitData.getId() + " ...");
                    double result = dbHandler.ProfitProduct(profitData.getId());
                    out.writeDouble(result);
                }

                // СОРТИРОВКА
                if(obj instanceof ShowForSorting)
                {
                    ShowForSorting sorting = (ShowForSorting) obj;
                    System.out.println("Запрос на сортировку типа " + sorting.getType() + " ...");
                    out.writeObject(dbHandler.Sorting(sorting.getType()));
                }

                // ФИЛЬТРАЦИЯ
                if(obj instanceof ShowForFilter)
                {
                    ShowForFilter filter = (ShowForFilter) obj;
                    System.out.println("Запрос фильтрации типа " + filter.getType() + " ...");
                    if(filter.getType() == 1) {
                        System.out.println("ОТ: " + filter.getOtStr() + " ДО: " + filter.getDoStr());
                        out.writeObject(dbHandler.Filter1(filter.getType(), filter.getOtInt(), filter.getDoInt()));
                    } else if(filter.getType() == 2) {
                        System.out.println("ОТ: " + filter.getOtDouble() + " ДО: " + filter.getDoDouble());
                        out.writeObject(dbHandler.Filter2(filter.getType(), filter.getOtDouble(), filter.getDoDouble()));
                    } else if(filter.getType() == 3) {
                        System.out.println("ОТ: " + filter.getOtInt() + " ДО: " + filter.getDoInt());
                        out.writeObject(dbHandler.Filter1(filter.getType(), filter.getOtInt(), filter.getDoInt()));
                    }  else if(filter.getType() == 4) {
                        System.out.println("ОТ: " + filter.getOtInt() + " ДО: " + filter.getDoInt());
                        out.writeObject(dbHandler.Filter1(filter.getType(), filter.getOtInt(), filter.getDoInt()));
                    }
                }

                // ПОИСК
                if(obj instanceof ShowForSearch)
                {
                    ShowForSearch search = (ShowForSearch) obj;
                    System.out.println("Запрос на поиск типа " + search.getType() + " ...");
                    if(search.getType() == 1 || search.getType() == 7 || search.getType() == 8) {
                        out.writeObject(dbHandler.Search1(search.getType(), search.getParametrInt()));
                    } else if(search.getType() == 2 || search.getType() == 3 || search.getType() == 4 || search.getType() == 5) {
                        out.writeObject(dbHandler.Search2(search.getType(), search.getParametrStr()));
                    } else if(search.getType() == 6) {
                        out.writeObject(dbHandler.Search3(search.getType(), search.getParametrDouble()));
                    }
                }



                //УДАЛЕНИЕ ТОВАРА
                if(obj instanceof DeleteProduct)
                {
                    DeleteProduct del = (DeleteProduct) obj;
                    System.out.println("Удаление информации о товара ID - " + del.getId() + "... ");
                    dbHandler.DelProduct(del.getId());
                }

                //РЕДАКТИРОВАНИЕ ИНФОРМАЦИИ О ТОВАРЕ
               if(obj instanceof ShowForReduct)
                {
                    ShowForReduct reduct = (ShowForReduct) obj;
                    System.out.println("Редактирование информации о товаре типа " +  reduct.getType() + "... ");
                    System.out.println("ID - " +  reduct.getId());
                    if(reduct.getType() == 1 || reduct.getType() == 2 || reduct.getType() == 3 || reduct.getType() == 4 || reduct.getType() == 5|| reduct.getType() == 6){
                        dbHandler.ReductProduct1(reduct.getType(), reduct.getId(), reduct.getParametrStr());
                    } else if(reduct.getType() == 7 || reduct.getType() == 10 || reduct.getType() ==11 || reduct.getType() == 12){
                        dbHandler.ReductProduct2(reduct.getType(), reduct.getId(), reduct.getParametrDouble());
                    }else if(reduct.getType() == 8 || reduct.getType() == 9){
                        dbHandler.ReductProduct3(reduct.getType(), reduct.getId(), reduct.getParametrInt());
                    }
                }

                out.flush();
                // возвращаемся в началло для считывания нового сообщения
            }
            ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            // основная рабочая часть //
            //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            // если условие выхода - верно выключаем соединения
            System.out.println("Клиент отключен");
            System.out.println("Закрытие соединения и каналов.");
            // закрываем сначала каналы сокета !
            in.close();
            out.close();
            // потом закрываем сокет общения с клиентом в нити моносервера
            clientDialog.close();
            System.out.println("Закрытие соединения и каналов отклонено!");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
