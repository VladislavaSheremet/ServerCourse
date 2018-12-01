package sample.Database;

import sample.Lists.*;
import sample.SerializeClasses.*;

import java.sql.*;

public class DatabaseHandler extends Configs{
    Connection dbConnections;
    public Connection getDbConnections() throws ClassNotFoundException, SQLException{
        String connectionString = "jdbc:mysql://" + dbHost + ":"
                + dbPort +"/" +dbName + "?" + "autoReconnect=true&useSSL=false&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
        Class.forName("com.mysql.cj.jdbc.Driver");
        dbConnections = DriverManager.getConnection(connectionString, dbUsers, dbPass);
        return dbConnections;
    }

    //МЕТОДЫ ДЛЯ РАБОТЫ С ДАННЫМИ И БД

    //АВТОРИЗАЦИЯ
    public ResultSet Autorization(String login, String password) throws SQLException, ClassNotFoundException {
        ResultSet resSet = null;

        String select = "SELECT * FROM " + Const.USER_TABLE + " WHERE " +
                Const.USERS_LOGIN + " =? AND " + Const.USERS_PASSWORD + " =?";
        try {
            PreparedStatement prSt = getDbConnections().prepareStatement(select);
            prSt.setString(1, login);
            prSt.setString(2, password);
            resSet = prSt.executeQuery();
        } catch(SQLException e){
            e.printStackTrace();
        }
        return resSet;
    }

    public String AutorizationStatus(String login) throws SQLException, ClassNotFoundException {
        ResultSet resSet = null;

        String status = "";
        String select = " SELECT status" +
                        " FROM users" +
                        " WHERE login = \'" + login + "\'";
        try {
            PreparedStatement prSt = getDbConnections().prepareStatement(select);
            ResultSet rs = prSt.executeQuery(select);
            while (rs.next()) {
                status = rs.getString(1);
                System.out.println("СТАТУС: " + status);
            }
        } catch(SQLException e){
            e.printStackTrace();
        }
        return status;
    }

    public Boolean AutorizationBlackList(String login) throws SQLException, ClassNotFoundException {
        ResultSet resSet = null;
        String blackList = "";
        boolean List = false;
        String select = " SELECT blackList" +
                " FROM users" +
                " WHERE login = \'" + login + "\'";
        try {
            PreparedStatement prSt = getDbConnections().prepareStatement(select);
            ResultSet rs = prSt.executeQuery(select);
            while (rs.next()) {
                blackList = rs.getString(1);
                if(blackList.equals("true")) {
                    List = true;
                } else{
                    List = false;
                }
            }
        } catch(SQLException e){
            e.printStackTrace();
        }
        return List;
    }

    //РЕГИСТРАЦИЯ
    public void Registration(String UserName, String UserSurname, String Email, String TelephoneNumber,
                             String Login, String Password) throws SQLException, ClassNotFoundException {

        String status = "user";
        String insertRegistration = "INSERT INTO " + Const.USER_TABLE + "(" +
                Const.USERS_USERNAME + "," + Const.USERS_USERSURNAME + "," +
                Const.USERS_EMAIL + "," + Const.USERS_TELEPHONE + "," +
                Const.USERS_LOGIN + "," + Const.USERS_PASSWORD + "," + Const.USERS_STATUS + ") " +
                "VALUES(?,?,?,?,?,?,?)";
        try {
            PreparedStatement prSt = getDbConnections().prepareStatement(insertRegistration);
            prSt.setString(1, UserName);
            prSt.setString(2, UserSurname);
            prSt.setString(3, Email);
            prSt.setString(4, TelephoneNumber);
            prSt.setString(5, Login);
            prSt.setString(6, Password);
            prSt.setString(7, status);
            prSt.executeUpdate();
        } catch(SQLException e){
            e.printStackTrace();
        }
    }

    public ResultSet RegistrationProv(String login) throws SQLException, ClassNotFoundException {
        ResultSet resSet = null;
        String select = "SELECT * FROM " + Const.USER_TABLE + " WHERE " +
                Const.USERS_LOGIN + "= ?";
        try {
            PreparedStatement prSt = getDbConnections().prepareStatement(select);
            prSt.setString(1, login);
            resSet = prSt.executeQuery();
        } catch(SQLException e){
            e.printStackTrace();
        }
        return resSet;
    }

    //ДОБАВЛЕНИЕ В ЧЕРНЫЙ СПИСОК
    public Boolean BlackList(String login) throws SQLException, ClassNotFoundException {

        String select = "UPDATE users " +
                        "SET blackList = \'true\' " +
                        "WHERE login = '" + login + "'";
        try {
            PreparedStatement prSt = getDbConnections().prepareStatement(select);
            prSt.executeUpdate();
        } catch(SQLException e){
            e.printStackTrace();
        }
        return true;
    }

    //УДАЛЕНИЕ ИЗ ЧЕРНОГО СПИСКА
    public Boolean BlackListDel(String login) throws SQLException, ClassNotFoundException {

        String select = "UPDATE users " +
                "SET blackList = \'\' " +
                "WHERE login = '" + login + "'";
        try {
            PreparedStatement prSt = getDbConnections().prepareStatement(select);
            prSt.executeUpdate();
        } catch(SQLException e){
            e.printStackTrace();
        }
        return true;
    }

    //ДОБАВЛЕНИЕ В ЧЕРНЫЙ СПИСОК
    public Boolean BuyList(int id) throws SQLException, ClassNotFoundException {
        boolean resultt = false;
        String select1 = "SELECT klScklad" +
                        " FROM specifications" +
                        " WHERE idspecifications = " + id;

        PreparedStatement prSt1 = getDbConnections().prepareStatement(select1);
        ResultSet rs = prSt1.executeQuery(select1);
        int idSp = 0;
        while (rs.next()) {
            idSp = rs.getInt(1);
        }

        if(idSp > 1) {
            String select = "UPDATE specifications " +
                    "SET klScklad = (klScklad - 1),  klProdano = (klProdano + 1)" +
                    "WHERE  idspecifications = " + id;
            try {
                PreparedStatement prSt = getDbConnections().prepareStatement(select);
                prSt.executeUpdate();
                resultt = true;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }else{resultt = false;}
        return resultt;
    }

    //ДОБАВЛЕНИЕ НОВОГО ТОВАРА
    public void AddNewProduct(String categories, String productName, String productDescription, String material, String color,
            String country, double price, int klSklad, String information, double grossProfit, double sellingExpenses,
                              double managementExpenses) throws SQLException, ClassNotFoundException {
        int category1 = 0;
        if(categories.equals("Столы и стулья")){
                 category1 = 1;
        } else if(categories.equals("Мягкая мебель")){
            category1 = 2;
        }else if(categories.equals("Тумбы и комоды")){
            category1 = 3;
        }else if(categories.equals("Кухни")){
            category1 = 4;
        }else if(categories.equals("Офисная мебель")){
            category1 = 5;
        }else if(categories.equals("Шкафы")){
            category1 = 6;
        }else if(categories.equals("Гостинные")){
            category1 = 7;
        }else if(categories.equals("Другое")){
            category1 = 8;
        }
        String insertNewProduct1 = "INSERT INTO " + Const.FURNITURE_TABLE + "(" + Const.FURNITURE_NAME + ", " + Const.FURNITURE_DEFINITION+ ", " + Const.FURNITURE_ID_CATEGORY + ")" + " VALUES(?, ?, ?)";
        try {
            PreparedStatement prSt = getDbConnections().prepareStatement(insertNewProduct1);
            prSt.setString(1, productName);
            prSt.setString(2, productDescription);
            prSt.setInt(3, category1);
            prSt.executeUpdate();
        } catch(SQLException e){
            e.printStackTrace();
            e.printStackTrace();
        }
        String query = "SELECT idFurniture FROM furniture ORDER BY idFurniture DESC";
        PreparedStatement prSt1 = getDbConnections().prepareStatement(query);
        ResultSet rs = prSt1.executeQuery(query);
        int count=0;
        int id = 0;
        while (rs.next()) {
            id = rs.getInt(1);
            break;
        }
        String insertNewProduct2 = "INSERT INTO " + Const.SPECIFICATIONS_TABLE + "(" + Const.SPECIFICATIONS_COST + ", " + Const.SPECIFICATIONS_MATERIAL
                + ", " + Const.SPECIFICATIONS_COLOR + ", " + Const.SPECIFICATIONS_COUNTRY + ", " + Const.SPECIFICATIONS_KLSKLAD + ", " + Const.SPECIFICATIONS_ADDITIONAL
                + ", " + Const.SPECIFICATIONS_FURNITURE_ID + ", klProdano) "
                + "VALUES(?,?,?,?,?,?,?, ?)";
        try {
            PreparedStatement prSt = getDbConnections().prepareStatement(insertNewProduct2);
            prSt.setDouble(1, price);
            prSt.setString(2, material);
            prSt.setString(3, color);
            prSt.setString(4, country);
            prSt.setInt(5, klSklad);
            prSt.setString(6, information);
            prSt.setInt(7, id);
            prSt.setInt(8, 0);
            prSt.executeUpdate();
        } catch(SQLException e){
            e.printStackTrace();
        }
        String insertNewProduct3 = "INSERT INTO " + Const.DATAPROFIT_TABLE + "(" + Const.DATAPROFIT_GROSS + ", " + Const.DATAPROFIT_SELLING
                + ", " + Const.DATAPROFIT_MANAGEMENT + ", " + Const.DATAPROFIT_FURNITURE_ID + ") " + "VALUES(?,?,?,?)";
        try {
            PreparedStatement prSt = getDbConnections().prepareStatement(insertNewProduct3);
            prSt.setDouble(1, grossProfit);
            prSt.setDouble(2, sellingExpenses);
            prSt.setDouble(3, managementExpenses);
            prSt.setInt(4, id);
            prSt.executeUpdate();
        } catch(SQLException e){
            e.printStackTrace();
        }
    }


    /*ПРОСМОТРЫ СПИСКОВ*/

    //ПРОСМОТР СПИСКА ПРОДУКЦИИ
    public ListOfFurniture ShowProduct() {
        ListOfFurniture listOfFurniture = new ListOfFurniture();
        System.out.println("Информация о продукции: ");
        String select = "SELECT furniture.idFurniture, furniture.Name_furniture, furniture.ShortDefinitionFurniture, furniture.idCategory, " +
                        "specifications.additionalInformation, specifications.material, specifications.color, specifications.cost, specifications.klScklad, specifications.klProdano, specifications.producingCountry, " +
                        "dataforprofit.grossProfit, dataforprofit.sellingExpenses, dataforprofit.managementExpenses" +
                        " FROM furniture" +
                        " INNER JOIN specifications ON furniture.idFurniture = specifications.idFurniture" +
                        " INNER JOIN dataforprofit ON furniture.idFurniture = dataforprofit.idFurniture";
        try {
            PreparedStatement prSt = getDbConnections().prepareStatement(select);
            ResultSet rs = prSt.executeQuery(select);
            int id = 0;
            String name;
            String shortdescription;
            int idCategory;
            String Category = null;
            double cost;
            int klSklad;
            int klBuy;
            String material;
            String color;
            String country;
            String information;
            double gross;
            double selling;
            double management;
            while (rs.next()) {
                id = rs.getInt(1);
                name = rs.getString(2);
                shortdescription = rs.getString(3);
                idCategory = rs.getInt(4);
                information = rs.getString(5);
                material = rs.getString(6);
                color = rs.getString(7);
                cost = rs.getDouble(8);
                klSklad = rs.getInt(9);
                klBuy = rs.getInt(10);
                country = rs.getString(11);
                gross = rs.getDouble(12);
                selling = rs.getDouble(13);
                management = rs.getDouble(14);
                if(idCategory == 1)
                {
                    Category = "Столы и стулья";
                } else if(idCategory == 2){
                    Category = "Мягкая мебель";
                } else if(idCategory == 3){
                    Category = "Тумбы и комоды";
                }else if(idCategory == 4){
                    Category = "Кухни";
                }else if(idCategory == 5){
                    Category = "Офисная мебель";
                }else if(idCategory == 6){
                    Category = "Шкафы";
                }else if(idCategory == 7){
                    Category = "Гостинные";
                }else if(idCategory == 8){
                    Category = "Другое";
                }
                ShowInrormationAboutProductData furniture = new ShowInrormationAboutProductData();
                furniture.setId(id);
                furniture.setName(name);
                furniture.setDescription(shortdescription);
                furniture.setCategory(Category);
                furniture.setAdditionalInformation(information);
                furniture.setMaterial(material);
                furniture.setColor(color);
                furniture.setPrice(cost);
                furniture.setKlSklad(klSklad);
                furniture.setKlBuy(klBuy);
                furniture.setCountry(country);
                furniture.setGross(gross);
                furniture.setSelling(selling);
                furniture.setManagement(management);
                listOfFurniture.listOfFurniture.add(furniture);
                System.out.println(" ID: " + id + " Name: " + name + " ShortDescrription: " + shortdescription + " Category: " + Category
                + " Information: " + information + " Material: " + material + " Color: " + color + " Cost: " + cost + " KlSklad: " + klSklad + " KlBuy: " + klBuy + " Country: " + country
                + " Gross: " + gross + " Selling: " + selling + " Management: " + management);
            }
        } catch(SQLException e){
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return listOfFurniture;
    }

    //ПРОСМОТР СПИСКА РЕЗУЛЬТАТОВ
    public ListOfResult ShowResult() {
        ListOfResult listOfFurniture = new ListOfResult();
        System.out.println("Информация о продукции: ");
        String select = "SELECT profitresults.idprofitResults, furniture.Name_furniture, furniture.idCategory, " +
                "specifications.cost, specifications.klScklad, specifications.klProdano, " +
                "dataforprofit.grossProfit, dataforprofit.sellingExpenses, dataforprofit.managementExpenses, profitresults.result" +
                " FROM furniture" +
                " INNER JOIN specifications ON furniture.idFurniture = specifications.idFurniture" +
                " INNER JOIN dataforprofit ON furniture.idFurniture = dataforprofit.idFurniture" +
                " INNER JOIN profitresults ON dataforprofit.iddataForProfit = profitresults.iddataForProfit";
        try {
            PreparedStatement prSt = getDbConnections().prepareStatement(select);
            ResultSet rs = prSt.executeQuery(select);
            int id = 0;
            String name;
            int idCategory;
            String Category = null;
            double cost;
            int klSklad;
            int klBuy;
            double gross;
            double selling;
            double management;
            double result;
            while (rs.next()) {
                id = rs.getInt(1);
                name = rs.getString(2);
                idCategory = rs.getInt(3);
                cost = rs.getDouble(4);
                klSklad = rs.getInt(5);
                klBuy = rs.getInt(6);
                gross = rs.getDouble(7);
                selling = rs.getDouble(8);
                management = rs.getDouble(9);
                result = rs.getDouble(10);
                if(idCategory == 1)
                {
                    Category = "Столы и стулья";
                } else if(idCategory == 2){
                    Category = "Мягкая мебель";
                } else if(idCategory == 3){
                    Category = "Тумбы и комоды";
                }else if(idCategory == 4){
                    Category = "Кухни";
                }else if(idCategory == 5){
                    Category = "Офисная мебель";
                }else if(idCategory == 6){
                    Category = "Шкафы";
                }else if(idCategory == 7){
                    Category = "Гостинные";
                }else if(idCategory == 8){
                    Category = "Другое";
                }
                ShowResultProfit furniture = new ShowResultProfit();
                furniture.setId(id);
                furniture.setName(name);
                furniture.setCategory(Category);
                furniture.setPrice(cost);
                furniture.setKlSklad(klSklad);
                furniture.setKlBuy(klBuy);
                furniture.setGross(gross);
                furniture.setSelling(selling);
                furniture.setManagement(management);
                furniture.setResult(result);
                listOfFurniture.listOfFurniture.add(furniture);
                System.out.println(" ID: " + id + " Name: " + name  + " Category: " + Category
                        + " Cost: " + cost + " KlSklad: " + klSklad + " KlBuy: " + klBuy
                        + " Gross: " + gross + " Selling: " + selling + " Management: " + management + " Result: " + result);
            }
        } catch(SQLException e){
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return listOfFurniture;
    }

    //ДАННЫЕ ДЛЯ ГИСТОГРАММЫ
    public ListForGistogram DataGistogram() {
        ListForGistogram list = new ListForGistogram();
        System.out.println("Результаты для построения гистограммы: ");
        String select = "SELECT profitresults.idprofitResults, furniture.Name_furniture, profitresults.result" +
                " FROM furniture" +
                " INNER JOIN specifications ON furniture.idFurniture = specifications.idFurniture" +
                " INNER JOIN dataforprofit ON furniture.idFurniture = dataforprofit.idFurniture" +
                " INNER JOIN profitresults ON dataforprofit.iddataForProfit = profitresults.iddataForProfit";
        try {
            PreparedStatement prSt = getDbConnections().prepareStatement(select);
            ResultSet rs = prSt.executeQuery(select);
            int id;
            String name;
            double result;
            while (rs.next()) {
                id = rs.getInt(1);
                name = rs.getString(2);
                result = rs.getDouble(3);

                DataForGistogram furniture = new DataForGistogram();
                furniture.setId(id);
                furniture.setName(name);
                furniture.setResult(result);
                list.listForGistogram.add(furniture);
                System.out.println(" ID: " + id + " Name: " + name  + " Result: " + result);
            }
        } catch(SQLException e){
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return list;
    }


    public ListForAnalis DataAnalis() {
        ListForAnalis list = new ListForAnalis();
        System.out.println("Результаты для построения гистограммы: ");
        String select = "SELECT furniture.Name_furniture, specifications.klProdano" +
                        " FROM furniture" +
                        " INNER JOIN specifications ON furniture.idFurniture = specifications.idFurniture";
        try {
            PreparedStatement prSt = getDbConnections().prepareStatement(select);
            ResultSet rs = prSt.executeQuery(select);
            String name;
            int kl;
            while (rs.next()) {
                name = rs.getString(1);
                kl = rs.getInt(2);

                DataForAnalis furniture = new DataForAnalis();
                furniture.setName(name);
                furniture.setKl(kl);
                list.listForAnalis.add(furniture);
                System.out.println(" Name: " + name  + " Result: " + kl);
            }
        } catch(SQLException e){
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return list;
    }




    //ПРОСМОТР СПИСКА ПОЛЬЗОВАТЕЛЕЙ
    public ListOfUsers ShowUsers() {
        ListOfUsers listOfUsers = new  ListOfUsers();
        System.out.println("Информация о пользователях: ");
        String select = "SELECT idusers, login, usersname, usersurname, telephonenumber, email, blackList" +
                " FROM users " +
                " WHERE status = \'user\'";
        try {
            PreparedStatement prSt = getDbConnections().prepareStatement(select);
            ResultSet rs = prSt.executeQuery(select);
            int id = 0;
            String login;
            String name;
            String surname;
            String telephone;
            String email;
            String black;
            while (rs.next()) {
                id = rs.getInt(1);
                login = rs.getString(2);
                name = rs.getString(3);
                surname = rs.getString(4);
                telephone = rs.getString(5);
                email = rs.getString(6);
                black = rs.getString(7);
                ShowClientData client = new ShowClientData();
                client.setId(id);
                client.setLogin(login);
                client.setName(name);
                client.setSurname(surname);
                client.setTelephone(telephone);
                client.setEmail(email);
                client.setBlack(black);
                listOfUsers.listOfUser.add(client);
                System.out.println(" ID: " + id + " Login: " + login +" Name: " + name + " Surname: " + surname + " Telephone: " + telephone
                        + " Email: " + email + " Black: " + black);
            }
        } catch(SQLException e){
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return listOfUsers;
    }

    //ВЫВОД ДАННЫХ ПО КАТЕГОРИЯМ
    public ListOfFurnitureCategories ShowCategory(int category) {
        ListOfFurnitureCategories listOfFurniture = new ListOfFurnitureCategories();
        System.out.println("Информация о продукции категории " + category + ":");
        String select = "SELECT furniture.idFurniture, furniture.Name_furniture, furniture.ShortDefinitionFurniture, " +
                "specifications.additionalInformation, specifications.material, specifications.color, specifications.cost, specifications.klScklad, specifications.klProdano,  specifications.producingCountry, " +
                "dataforprofit.grossProfit, dataforprofit.sellingExpenses, dataforprofit.managementExpenses" +
                " FROM furniture" +
                " INNER JOIN specifications ON furniture.idFurniture = specifications.idFurniture" +
                " INNER JOIN dataforprofit ON furniture.idFurniture = dataforprofit.idFurniture " +
                " WHERE furniture.idCategory = " + category;
        try {
            PreparedStatement prSt = getDbConnections().prepareStatement(select);
            ResultSet rs = prSt.executeQuery(select);
            int id = 0;
            String name; String shortdescription;
            double cost; String material;
            int klSklad;
            int klBuy;
            String color; String country;
            String information; double gross;
            double selling; double management;
            while (rs.next()) {
                id = rs.getInt(1);
                name = rs.getString(2);
                shortdescription = rs.getString(3);
                information = rs.getString(4);
                material = rs.getString(5);
                color = rs.getString(6);
                cost = rs.getDouble(7);
                klSklad = rs.getInt(8);
                klBuy = rs.getInt(9);
                country = rs.getString(10);
                gross = rs.getDouble(11);
                selling = rs.getDouble(12);
                management = rs.getDouble(13);
                ShowInformationForCategory furniture = new ShowInformationForCategory();
                furniture.setId(id);
                furniture.setName(name);
                furniture.setDescription(shortdescription);
                furniture.setAdditionalInformation(information);
                furniture.setMaterial(material);
                furniture.setColor(color);
                furniture.setPrice(cost);
                furniture.setKlScklad(klSklad);
                furniture.setKlBuy(klBuy);
                furniture.setCountry(country);
                furniture.setGross(gross);
                furniture.setSelling(selling);
                furniture.setManagement(management);
                listOfFurniture.listOfFurniture.add(furniture);
                System.out.println(" ID: " + id + " Name: " + name + " ShortDescrription: " + shortdescription +
                        " Information: " + information + " Material: " + material + " Color: " + color + " Cost: " + cost + " KlSklad: " + klSklad
                        + " KlBuy: " + klBuy + " Country: " + country
                        + " Gross: " + gross + " Selling: " + selling + " Management: " + management);
            }
        } catch(SQLException e){
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return listOfFurniture;
    }

    /*РАСЧЕТЫ ПРИБЫЛИ*/

    //РАСЧЕТ ПРИБЫЛИ ОТ ПРОДАЖ ВСЕЙ ПРОДУКЦИИ
    public double ProfitAll() {
        System.out.println("Данные для расчетов прибыли от продаж всей продукции салона: ");
        double cost = 0;
        int kl = 0;
        double revenue = 0;
        double revenueres = 0;
        double gross=0;
        double gross1=0;
        double grosss=0;
        double selling=0;
        double management=0;

        String select = "SELECT specifications.cost, specifications.klProdano, dataforprofit.grossProfit, dataforprofit.sellingExpenses, dataforprofit.managementExpenses" +
                        " FROM furniture" +
                        " INNER JOIN specifications ON furniture.idFurniture = specifications.idFurniture" +
                        " INNER JOIN dataforprofit ON furniture.idFurniture = dataforprofit.idFurniture ";
        try {
            PreparedStatement prSt = getDbConnections().prepareStatement(select);
            ResultSet rs = prSt.executeQuery(select);
            while (rs.next()) {
                cost = rs.getDouble(1);
                kl = rs.getInt(2);
                gross = rs.getDouble(3);

                //выручка
                revenue = (cost*kl);
                revenueres += revenue;

                //себестоимость
                gross1 += (kl*gross);

                //коммерческие и управленческие затраты
                if(kl != 0) {
                    selling += rs.getDouble(4);
                    management += rs.getDouble(5);
                }
            }
        } catch(SQLException e){
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        double result;
        double valProfit;
        valProfit = revenueres - gross1;
        System.out.println("Данные для расчета прибыли: ");
        System.out.println("Выручка от продаж: " + revenueres + "$" + " \nСуммарная себестоимость: "  + gross1 + " \nКоммерческие затраты: "+ selling + "$" +  " \nУправленческие затраты: " + management + "$");
        System.out.println("Уровень валовой прибыли: " + valProfit);
        result = valProfit - selling - management;
        System.out.println("ПРИБЫЛЬ ОТ ПРОДАЖ: " + result + "$");
        return result;
    }

    //РАСЧЕТ ПРИБЫЛИ ОТ ПРОДАЖ ПО КАТЕГОРИИ ТОВАРОВ
    public double ProfitCategory1(int category) {
        System.out.println("Данные для расчетов прибыли от продаж по "+ category+ " категории товаров: ");
        double cost = 0;
        int kl = 0;
        double revenue = 0;
        double revenueres = 0;
        double gross=0;
        double gross1=0;
        double grosss=0;
        double selling=0;
        double management=0;

        String select =  "SELECT furniture.idCategory, specifications.cost, specifications.klProdano, dataforprofit.grossProfit, dataforprofit.sellingExpenses, dataforprofit.managementExpenses" +
                        " FROM furniture" +
                        " INNER JOIN specifications ON furniture.idFurniture = specifications.idFurniture" +
                        " INNER JOIN dataforprofit ON furniture.idFurniture = dataforprofit.idFurniture " +
                        " WHERE idCategory = \'" + category + "\'";
        try {
            PreparedStatement prSt = getDbConnections().prepareStatement(select);
            ResultSet rs = prSt.executeQuery(select);
            while (rs.next()) {
                cost = rs.getDouble(2);
                kl = rs.getInt(3);
                gross = rs.getDouble(4);

                //выручка
                revenue = (cost*kl);
                revenueres += revenue;

                //себестоимость
                gross1 += (kl*gross);

                //коммерческие и управленческие затраты
                if(kl != 0) {
                    selling += rs.getDouble(5);
                    management += rs.getDouble(6);
                }
            }
        } catch(SQLException e){
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        double result;
        double valProfit;
        valProfit = revenueres - gross1;
        System.out.println("Данные для расчета прибыли: ");
        System.out.println("Выручка от продаж: " + revenueres + "$" + " \nСуммарная себестоимость: "  + gross1 + " \nКоммерческие затраты: "+ selling + "$" +  " \nУправленческие затраты: " + management + "$");
        System.out.println("Уровень валовой прибыли: " + valProfit);
        result = valProfit - selling - management;
        System.out.println("ПРИБЫЛЬ ОТ ПРОДАЖ: " + result + "$");
        return result;
    }

    //РАСЧЕТ ПРИБЫЛИ ОТ ПРОДАЖ ОПРЕДЕЛЕННОГО ТОВАРА
    public double ProfitProduct(int id) {
        System.out.println("Данные для расчетов прибыли от продаж товара с ID = " + id + " : ");
        double cost = 0;
        int kl = 0;
        int idd = 0;
        double revenue = 0;
        double revenueres = 0;
        double gross=0;
        double gross1=0;
        double grosss=0;
        double selling=0;
        double management=0;

        String select =  "SELECT specifications.cost, specifications.klProdano, dataforprofit.grossProfit, dataforprofit.sellingExpenses, dataforprofit.managementExpenses" +
                " FROM furniture" +
                " INNER JOIN specifications ON furniture.idFurniture = specifications.idFurniture" +
                " INNER JOIN dataforprofit ON furniture.idFurniture = dataforprofit.idFurniture " +
                " WHERE specifications.idFurniture = " + id + " AND dataforprofit.idFurniture = " + id;
        try {
            PreparedStatement prSt = getDbConnections().prepareStatement(select);
            ResultSet rs = prSt.executeQuery(select);
            while (rs.next()) {
                cost = rs.getDouble(1);
                kl = rs.getInt(2);
                gross = rs.getDouble(3);
                idd = rs.getInt(4);

                //выручка
                revenue = (cost*kl);
                revenueres += revenue;

                //себестоимость
                gross1 += (kl*gross);

                //коммерческие и управленческие затраты
                if(kl != 0) {
                    selling += rs.getDouble(4);
                    management += rs.getDouble(5);
                }
            }
        } catch(SQLException e){
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        double result;
        double valProfit;
        valProfit = revenueres - gross1;
        System.out.println("Данные для расчета прибыли: ");
        System.out.println("Выручка от продаж: " + revenueres + "$" + " \nСуммарная себестоимость: "  + gross1 + " \nКоммерческие затраты: "+ selling + "$" +  " \nУправленческие затраты: " + management + "$");
        System.out.println("Уровень валовой прибыли: " + valProfit);
        result = valProfit - selling - management;
        System.out.println("ПРИБЫЛЬ ОТ ПРОДАЖ: " + result + "$");

        String select1 =  " SELECT profitresults.iddataForProfit" +
                          " FROM profitresults " +
                          " WHERE profitresults.iddataForProfit = " + id;
        int counter = 0;
        try {
            PreparedStatement prSt = getDbConnections().prepareStatement(select1);
            ResultSet rs = prSt.executeQuery(select1);
            while (rs.next()) {
                idd = rs.getInt(1);
                counter++;
            }
        } catch(SQLException e){
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        if(counter == 0) {
            String insert = "INSERT INTO profitresults (iddataForProfit, result)" + " VALUES(?, ?)";
            try {
                PreparedStatement prSt = getDbConnections().prepareStatement(insert);
                prSt.setInt(1, id);
                prSt.setDouble(2, result);
                prSt.executeUpdate();
                System.out.println("Данные занесены в базу!");
            } catch (SQLException e) {
                e.printStackTrace();
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }else{
            String select2 = "UPDATE profitresults " +
                     "SET profitresults.result = " + result + " " +
                     "WHERE profitresults.iddataForProfit = " + id;
            try {
              PreparedStatement prSt = getDbConnections().prepareStatement(select2);
              prSt.executeUpdate();
                System.out.println("Данные изменены в базе!");
          } catch(SQLException e){
             e.printStackTrace();
         } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        }
        return result;
    }

    /*РАБОТА С ДАННЫМИ*/
    //СОРТИРОВКА
    public ListOfSort Sorting(int type) {
        ListOfSort list = new ListOfSort();
        System.out.println("Сортировка вида " + type + ":");
        String select = "";
        if(type == 1) {
            select = "SELECT furniture.idFurniture, furniture.Name_furniture, furniture.ShortDefinitionFurniture, furniture.idCategory, " +
                    "specifications.additionalInformation, specifications.material, specifications.color, specifications.cost, specifications.klScklad, specifications.klProdano, specifications.producingCountry, " +
                    "dataforprofit.grossProfit, dataforprofit.sellingExpenses, dataforprofit.managementExpenses" +
                    " FROM furniture" +
                    " INNER JOIN specifications ON furniture.idFurniture = specifications.idFurniture" +
                    " INNER JOIN dataforprofit ON furniture.idFurniture = dataforprofit.idFurniture " +
                    " ORDER BY furniture.Name_furniture";
        } else if(type == 2){
            select = "SELECT furniture.idFurniture, furniture.Name_furniture, furniture.ShortDefinitionFurniture, furniture.idCategory, " +
                    "specifications.additionalInformation, specifications.material, specifications.color, specifications.cost, specifications.klScklad, specifications.klProdano, specifications.producingCountry, " +
                    "dataforprofit.grossProfit, dataforprofit.sellingExpenses, dataforprofit.managementExpenses" +
                    " FROM furniture" +
                    " INNER JOIN specifications ON furniture.idFurniture = specifications.idFurniture" +
                    " INNER JOIN dataforprofit ON furniture.idFurniture = dataforprofit.idFurniture " +
                    " ORDER BY furniture.Name_furniture DESC";
        }else if(type == 3){
            select = "SELECT furniture.idFurniture, furniture.Name_furniture, furniture.ShortDefinitionFurniture, furniture.idCategory, " +
                    "specifications.additionalInformation, specifications.material, specifications.color, specifications.cost, specifications.klScklad, specifications.klProdano, specifications.producingCountry, " +
                    "dataforprofit.grossProfit, dataforprofit.sellingExpenses, dataforprofit.managementExpenses" +
                    " FROM furniture" +
                    " INNER JOIN specifications ON furniture.idFurniture = specifications.idFurniture" +
                    " INNER JOIN dataforprofit ON furniture.idFurniture = dataforprofit.idFurniture " +
                    " ORDER BY  specifications.cost";
        }else if(type == 4){
            select = "SELECT furniture.idFurniture, furniture.Name_furniture, furniture.ShortDefinitionFurniture, furniture.idCategory, " +
                    "specifications.additionalInformation, specifications.material, specifications.color, specifications.cost, specifications.klScklad, specifications.klProdano, specifications.producingCountry, " +
                    "dataforprofit.grossProfit, dataforprofit.sellingExpenses, dataforprofit.managementExpenses" +
                    " FROM furniture" +
                    " INNER JOIN specifications ON furniture.idFurniture = specifications.idFurniture" +
                    " INNER JOIN dataforprofit ON furniture.idFurniture = dataforprofit.idFurniture " +
                    " ORDER BY  specifications.cost DESC";
        } else if(type == 5){
            select = "SELECT furniture.idFurniture, furniture.Name_furniture, furniture.ShortDefinitionFurniture, furniture.idCategory, " +
                    "specifications.additionalInformation, specifications.material, specifications.color, specifications.cost, specifications.klScklad, specifications.klProdano, specifications.producingCountry, " +
                    "dataforprofit.grossProfit, dataforprofit.sellingExpenses, dataforprofit.managementExpenses" +
                    " FROM furniture" +
                    " INNER JOIN specifications ON furniture.idFurniture = specifications.idFurniture" +
                    " INNER JOIN dataforprofit ON furniture.idFurniture = dataforprofit.idFurniture " +
                    " ORDER BY  specifications.producingCountry";
        }else if(type == 6){
            select = "SELECT furniture.idFurniture, furniture.Name_furniture, furniture.ShortDefinitionFurniture, furniture.idCategory, " +
                    "specifications.additionalInformation, specifications.material, specifications.color, specifications.cost, specifications.klScklad, specifications.klProdano, specifications.producingCountry, " +
                    "dataforprofit.grossProfit, dataforprofit.sellingExpenses, dataforprofit.managementExpenses" +
                    " FROM furniture" +
                    " INNER JOIN specifications ON furniture.idFurniture = specifications.idFurniture" +
                    " INNER JOIN dataforprofit ON furniture.idFurniture = dataforprofit.idFurniture " +
                    " ORDER BY  specifications.producingCountry DESC";
        }
        try {
            PreparedStatement prSt = getDbConnections().prepareStatement(select);
            ResultSet rs = prSt.executeQuery(select);
            int id = 0;
            String name;
            String shortdescription;
            int idCategory;
            String Category = null;
            double cost;
            int klSklad;
            int klBuy;
            String material;
            String color;
            String country;
            String information;
            double gross;
            double selling;
            double management;
            while (rs.next()) {
                id = rs.getInt(1);
                name = rs.getString(2);
                shortdescription = rs.getString(3);
                idCategory = rs.getInt(4);
                information = rs.getString(5);
                material = rs.getString(6);
                color = rs.getString(7);
                cost = rs.getDouble(8);
                klSklad = rs.getInt(9);
                klBuy = rs.getInt(10);
                country = rs.getString(11);
                gross = rs.getDouble(12);
                selling = rs.getDouble(13);
                management = rs.getDouble(14);
                if(idCategory == 1)
                {
                    Category = "Столы и стулья";
                } else if(idCategory == 2){
                    Category = "Мягкая мебель";
                } else if(idCategory == 3){
                    Category = "Тумбы и комоды";
                }else if(idCategory == 4){
                    Category = "Кухни";
                }else if(idCategory == 5){
                    Category = "Офисная мебель";
                }else if(idCategory == 6){
                    Category = "Шкафы";
                }else if(idCategory == 7){
                    Category = "Гостинные";
                }else if(idCategory == 8){
                    Category = "Другое";
                }
                ShowForSorting furniture = new ShowForSorting();
                furniture.setId(id);
                furniture.setName(name);
                furniture.setDescription(shortdescription);
                furniture.setCategory(Category);
                furniture.setAdditionalInformation(information);
                furniture.setMaterial(material);
                furniture.setColor(color);
                furniture.setPrice(cost);
                furniture.setKlSklad(klSklad);
                furniture.setKlBuy(klBuy);
                furniture.setCountry(country);
                furniture.setGross(gross);
                furniture.setSelling(selling);
                furniture.setManagement(management);
                list.listOfSort.add(furniture);

                System.out.println(" ID: " + id + " Name: " + name + " ShortDescrription: " + shortdescription + " Category: " + Category
                        + " Information: " + information + " Material: " + material + " Color: " + color + " Cost: " + cost + " KlSklad: " + klSklad + " KlBuy: " + klBuy + " Country: " + country
                        + " Gross: " + gross + " Selling: " + selling + " Management: " + management);
            }
        } catch(SQLException e){
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return list;
    }

    //ФИЛЬТРАЦИЯ
    public ListOfFilter Filter1(int type,int Ot, int Do) {
        ListOfFilter list = new ListOfFilter();
        System.out.println("Фильтрация вида " + type + ":");
        String select ="";
        if(type == 1) {
            select = "SELECT furniture.idFurniture, furniture.Name_furniture, furniture.ShortDefinitionFurniture, furniture.idCategory, " +
                    "specifications.additionalInformation, specifications.material, specifications.color, specifications.cost, specifications.klScklad, specifications.klProdano, specifications.producingCountry, " +
                    "dataforprofit.grossProfit, dataforprofit.sellingExpenses, dataforprofit.managementExpenses" +
                    " FROM furniture" +
                    " INNER JOIN specifications ON furniture.idFurniture = specifications.idFurniture" +
                    " INNER JOIN dataforprofit ON furniture.idFurniture = dataforprofit.idFurniture " +
                    " WHERE furniture.idFurniture BETWEEN " + Ot + " AND " + Do;
        } else if(type == 3) {
            select = "SELECT furniture.idFurniture, furniture.Name_furniture, furniture.ShortDefinitionFurniture, furniture.idCategory, " +
                    "specifications.additionalInformation, specifications.material, specifications.color, specifications.cost, specifications.klScklad, specifications.klProdano, specifications.producingCountry, " +
                    "dataforprofit.grossProfit, dataforprofit.sellingExpenses, dataforprofit.managementExpenses" +
                    " FROM furniture" +
                    " INNER JOIN specifications ON furniture.idFurniture = specifications.idFurniture" +
                    " INNER JOIN dataforprofit ON furniture.idFurniture = dataforprofit.idFurniture " +
                    " WHERE specifications.klScklad BETWEEN " + Ot + " AND " + Do;
        } else if(type ==4){
            select = "SELECT furniture.idFurniture, furniture.Name_furniture, furniture.ShortDefinitionFurniture, furniture.idCategory, " +
                    "specifications.additionalInformation, specifications.material, specifications.color, specifications.cost, specifications.klScklad, specifications.klProdano, specifications.producingCountry, " +
                    "dataforprofit.grossProfit, dataforprofit.sellingExpenses, dataforprofit.managementExpenses" +
                    " FROM furniture" +
                    " INNER JOIN specifications ON furniture.idFurniture = specifications.idFurniture" +
                    " INNER JOIN dataforprofit ON furniture.idFurniture = dataforprofit.idFurniture " +
                    " WHERE specifications.klProdano BETWEEN " + Ot + " AND " + Do;
        }
        try {
            PreparedStatement prSt = getDbConnections().prepareStatement(select);
            ResultSet rs = prSt.executeQuery(select);
            int id = 0;
            String name;
            String shortdescription;
            int idCategory;
            String Category = null;
            double cost;
            int klSklad;
            int klBuy;
            String material;
            String color;
            String country;
            String information;
            double gross;
            double selling;
            double management;
            while (rs.next()) {
                id = rs.getInt(1);
                name = rs.getString(2);
                shortdescription = rs.getString(3);
                idCategory = rs.getInt(4);
                information = rs.getString(5);
                material = rs.getString(6);
                color = rs.getString(7);
                cost = rs.getDouble(8);
                klSklad = rs.getInt(9);
                klBuy = rs.getInt(10);
                country = rs.getString(11);
                gross = rs.getDouble(12);
                selling = rs.getDouble(13);
                management = rs.getDouble(14);
                if(idCategory == 1)
                {
                    Category = "Столы и стулья";
                } else if(idCategory == 2){
                    Category = "Мягкая мебель";
                } else if(idCategory == 3){
                    Category = "Тумбы и комоды";
                }else if(idCategory == 4){
                    Category = "Кухни";
                }else if(idCategory == 5){
                    Category = "Офисная мебель";
                }else if(idCategory == 6){
                    Category = "Шкафы";
                }else if(idCategory == 7){
                    Category = "Гостинные";
                }else if(idCategory == 8){
                    Category = "Другое";
                }
                ShowForFilter furniture = new ShowForFilter();
                furniture.setId(id);
                furniture.setName(name);
                furniture.setDescription(shortdescription);
                furniture.setCategory(Category);
                furniture.setAdditionalInformation(information);
                furniture.setMaterial(material);
                furniture.setColor(color);
                furniture.setPrice(cost);
                furniture.setKlSklad(klSklad);
                furniture.setKlBuy(klBuy);
                furniture.setCountry(country);
                furniture.setGross(gross);
                furniture.setSelling(selling);
                furniture.setManagement(management);
                list.listOfSort.add(furniture);

                System.out.println(" ID: " + id + " Name: " + name + " ShortDescrription: " + shortdescription + " Category: " + Category
                        + " Information: " + information + " Material: " + material + " Color: " + color + " Cost: " + cost + " KlSklad: " + klSklad + " KlBuy: " + klBuy + " Country: " + country
                        + " Gross: " + gross + " Selling: " + selling + " Management: " + management);
            }
        } catch(SQLException e){
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return list;
    }

    public ListOfFilter Filter2(int type,double Ot, double Do) {
        ListOfFilter list = new ListOfFilter();
        System.out.println("Фильтрация вида " + type + ":");
        String select = "SELECT furniture.idFurniture, furniture.Name_furniture, furniture.ShortDefinitionFurniture, furniture.idCategory, " +
                    "specifications.additionalInformation, specifications.material, specifications.color, specifications.cost, specifications.klScklad, specifications.klProdano, specifications.producingCountry, " +
                    "dataforprofit.grossProfit, dataforprofit.sellingExpenses, dataforprofit.managementExpenses" +
                    " FROM furniture" +
                    " INNER JOIN specifications ON furniture.idFurniture = specifications.idFurniture" +
                    " INNER JOIN dataforprofit ON furniture.idFurniture = dataforprofit.idFurniture " +
                    " WHERE specifications.cost BETWEEN " + Ot + " AND " + Do;
        try {
            PreparedStatement prSt = getDbConnections().prepareStatement(select);
            ResultSet rs = prSt.executeQuery(select);
            int id = 0;
            String name;
            String shortdescription;
            int idCategory;
            String Category = null;
            double cost;
            int klSklad;
            int klBuy;
            String material;
            String color;
            String country;
            String information;
            double gross;
            double selling;
            double management;
            while (rs.next()) {
                id = rs.getInt(1);
                name = rs.getString(2);
                shortdescription = rs.getString(3);
                idCategory = rs.getInt(4);
                information = rs.getString(5);
                material = rs.getString(6);
                color = rs.getString(7);
                cost = rs.getDouble(8);
                klSklad = rs.getInt(9);
                klBuy = rs.getInt(10);
                country = rs.getString(11);
                gross = rs.getDouble(12);
                selling = rs.getDouble(13);
                management = rs.getDouble(14);
                if(idCategory == 1)
                {
                    Category = "Столы и стулья";
                } else if(idCategory == 2){
                    Category = "Мягкая мебель";
                } else if(idCategory == 3){
                    Category = "Тумбы и комоды";
                }else if(idCategory == 4){
                    Category = "Кухни";
                }else if(idCategory == 5){
                    Category = "Офисная мебель";
                }else if(idCategory == 6){
                    Category = "Шкафы";
                }else if(idCategory == 7){
                    Category = "Гостинные";
                }else if(idCategory == 8){
                    Category = "Другое";
                }
                ShowForFilter furniture = new ShowForFilter();
                furniture.setId(id);
                furniture.setName(name);
                furniture.setDescription(shortdescription);
                furniture.setCategory(Category);
                furniture.setAdditionalInformation(information);
                furniture.setMaterial(material);
                furniture.setColor(color);
                furniture.setPrice(cost);
                furniture.setKlSklad(klSklad);
                furniture.setKlBuy(klBuy);
                furniture.setCountry(country);
                furniture.setGross(gross);
                furniture.setSelling(selling);
                furniture.setManagement(management);
                list.listOfSort.add(furniture);

                System.out.println(" ID: " + id + " Name: " + name + " ShortDescrription: " + shortdescription + " Category: " + Category
                        + " Information: " + information + " Material: " + material + " Color: " + color + " Cost: " + cost + " KlSklad: " + klSklad + " KlBuy: " + klBuy + " Country: " + country
                        + " Gross: " + gross + " Selling: " + selling + " Management: " + management);
            }
        } catch(SQLException e){
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return list;
    }

    /*ПОИСК*/
    public ListOfSearch Search1(int type,int parametr) {
        ListOfSearch list = new ListOfSearch();
        System.out.println("Фильтрация вида " + type + ":");
        String select ="";
        if(type == 1) {
            select = "SELECT furniture.idFurniture, furniture.Name_furniture, furniture.ShortDefinitionFurniture, furniture.idCategory, " +
                    "specifications.additionalInformation, specifications.material, specifications.color, specifications.cost, specifications.klScklad, specifications.klProdano, specifications.producingCountry, " +
                    "dataforprofit.grossProfit, dataforprofit.sellingExpenses, dataforprofit.managementExpenses" +
                    " FROM furniture" +
                    " INNER JOIN specifications ON furniture.idFurniture = specifications.idFurniture" +
                    " INNER JOIN dataforprofit ON furniture.idFurniture = dataforprofit.idFurniture " +
                    " WHERE furniture.idFurniture = " + parametr;
        } else if(type == 7) {
            select = "SELECT furniture.idFurniture, furniture.Name_furniture, furniture.ShortDefinitionFurniture, furniture.idCategory, " +
                    "specifications.additionalInformation, specifications.material, specifications.color, specifications.cost, specifications.klScklad, specifications.klProdano, specifications.producingCountry, " +
                    "dataforprofit.grossProfit, dataforprofit.sellingExpenses, dataforprofit.managementExpenses" +
                    " FROM furniture" +
                    " INNER JOIN specifications ON furniture.idFurniture = specifications.idFurniture" +
                    " INNER JOIN dataforprofit ON furniture.idFurniture = dataforprofit.idFurniture " +
                    " WHERE specifications.klScklad = " + parametr;
        } else if(type ==8){
            select = "SELECT furniture.idFurniture, furniture.Name_furniture, furniture.ShortDefinitionFurniture, furniture.idCategory, " +
                    "specifications.additionalInformation, specifications.material, specifications.color, specifications.cost, specifications.klScklad, specifications.klProdano, specifications.producingCountry, " +
                    "dataforprofit.grossProfit, dataforprofit.sellingExpenses, dataforprofit.managementExpenses" +
                    " FROM furniture" +
                    " INNER JOIN specifications ON furniture.idFurniture = specifications.idFurniture" +
                    " INNER JOIN dataforprofit ON furniture.idFurniture = dataforprofit.idFurniture " +
                    " WHERE specifications.klProdano = " + parametr;
        }
        try {
            PreparedStatement prSt = getDbConnections().prepareStatement(select);
            ResultSet rs = prSt.executeQuery(select);
            int id = 0;
            String name;
            String shortdescription;
            int idCategory;
            String Category = null;
            double cost;
            int klSklad;
            int klBuy;
            String material;
            String color;
            String country;
            String information;
            double gross;
            double selling;
            double management;
            while (rs.next()) {
                id = rs.getInt(1);
                name = rs.getString(2);
                shortdescription = rs.getString(3);
                idCategory = rs.getInt(4);
                information = rs.getString(5);
                material = rs.getString(6);
                color = rs.getString(7);
                cost = rs.getDouble(8);
                klSklad = rs.getInt(9);
                klBuy = rs.getInt(10);
                country = rs.getString(11);
                gross = rs.getDouble(12);
                selling = rs.getDouble(13);
                management = rs.getDouble(14);
                if(idCategory == 1)
                {
                    Category = "Столы и стулья";
                } else if(idCategory == 2){
                    Category = "Мягкая мебель";
                } else if(idCategory == 3){
                    Category = "Тумбы и комоды";
                }else if(idCategory == 4){
                    Category = "Кухни";
                }else if(idCategory == 5){
                    Category = "Офисная мебель";
                }else if(idCategory == 6){
                    Category = "Шкафы";
                }else if(idCategory == 7){
                    Category = "Гостинные";
                }else if(idCategory == 8){
                    Category = "Другое";
                }
                ShowForSearch furniture = new ShowForSearch();
                furniture.setId(id);
                furniture.setName(name);
                furniture.setDescription(shortdescription);
                furniture.setCategory(Category);
                furniture.setAdditionalInformation(information);
                furniture.setMaterial(material);
                furniture.setColor(color);
                furniture.setPrice(cost);
                furniture.setKlSklad(klSklad);
                furniture.setKlBuy(klBuy);
                furniture.setCountry(country);
                furniture.setGross(gross);
                furniture.setSelling(selling);
                furniture.setManagement(management);
                list.listOfSearch.add(furniture);

                System.out.println(" ID: " + id + " Name: " + name + " ShortDescrription: " + shortdescription + " Category: " + Category
                        + " Information: " + information + " Material: " + material + " Color: " + color + " Cost: " + cost + " KlSklad: " + klSklad + " KlBuy: " + klBuy + " Country: " + country
                        + " Gross: " + gross + " Selling: " + selling + " Management: " + management);
            }
        } catch(SQLException e){
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return list;
    }

    public ListOfSearch Search2(int type,String parametr) {
        ListOfSearch list = new ListOfSearch();
        System.out.println("Фильтрация вида " + type + ":");
        String select ="";
        if(type == 2) {
            select = "SELECT furniture.idFurniture, furniture.Name_furniture, furniture.ShortDefinitionFurniture, furniture.idCategory, " +
                    "specifications.additionalInformation, specifications.material, specifications.color, specifications.cost, specifications.klScklad, specifications.klProdano, specifications.producingCountry, " +
                    "dataforprofit.grossProfit, dataforprofit.sellingExpenses, dataforprofit.managementExpenses" +
                    " FROM furniture" +
                    " INNER JOIN specifications ON furniture.idFurniture = specifications.idFurniture" +
                    " INNER JOIN dataforprofit ON furniture.idFurniture = dataforprofit.idFurniture " +
                    " WHERE furniture.Name_furniture = \'" + parametr + "\'";
        } else if(type == 3) {
            select = "SELECT furniture.idFurniture, furniture.Name_furniture, furniture.ShortDefinitionFurniture, furniture.idCategory, " +
                    "specifications.additionalInformation, specifications.material, specifications.color, specifications.cost, specifications.klScklad, specifications.klProdano, specifications.producingCountry, " +
                    "dataforprofit.grossProfit, dataforprofit.sellingExpenses, dataforprofit.managementExpenses" +
                    " FROM furniture" +
                    " INNER JOIN specifications ON furniture.idFurniture = specifications.idFurniture" +
                    " INNER JOIN dataforprofit ON furniture.idFurniture = dataforprofit.idFurniture " +
                    " WHERE specifications.material = \'" + parametr + "\'";
        } else if(type ==4){
            select = "SELECT furniture.idFurniture, furniture.Name_furniture, furniture.ShortDefinitionFurniture, furniture.idCategory, " +
                    "specifications.additionalInformation, specifications.material, specifications.color, specifications.cost, specifications.klScklad, specifications.klProdano, specifications.producingCountry, " +
                    "dataforprofit.grossProfit, dataforprofit.sellingExpenses, dataforprofit.managementExpenses" +
                    " FROM furniture" +
                    " INNER JOIN specifications ON furniture.idFurniture = specifications.idFurniture" +
                    " INNER JOIN dataforprofit ON furniture.idFurniture = dataforprofit.idFurniture " +
                    " WHERE specifications.color = \'" + parametr + "\'";
        }else if(type ==5){
            select = "SELECT furniture.idFurniture, furniture.Name_furniture, furniture.ShortDefinitionFurniture, furniture.idCategory, " +
                    "specifications.additionalInformation, specifications.material, specifications.color, specifications.cost, specifications.klScklad, specifications.klProdano, specifications.producingCountry, " +
                    "dataforprofit.grossProfit, dataforprofit.sellingExpenses, dataforprofit.managementExpenses" +
                    " FROM furniture" +
                    " INNER JOIN specifications ON furniture.idFurniture = specifications.idFurniture" +
                    " INNER JOIN dataforprofit ON furniture.idFurniture = dataforprofit.idFurniture " +
                    " WHERE specifications.producingCountry = \'" + parametr + "\'";
        }
        try {
            PreparedStatement prSt = getDbConnections().prepareStatement(select);
            ResultSet rs = prSt.executeQuery(select);
            int id = 0;
            String name;
            String shortdescription;
            int idCategory;
            String Category = null;
            double cost;
            int klSklad;
            int klBuy;
            String material;
            String color;
            String country;
            String information;
            double gross;
            double selling;
            double management;
            while (rs.next()) {
                id = rs.getInt(1);
                name = rs.getString(2);
                shortdescription = rs.getString(3);
                idCategory = rs.getInt(4);
                information = rs.getString(5);
                material = rs.getString(6);
                color = rs.getString(7);
                cost = rs.getDouble(8);
                klSklad = rs.getInt(9);
                klBuy = rs.getInt(10);
                country = rs.getString(11);
                gross = rs.getDouble(12);
                selling = rs.getDouble(13);
                management = rs.getDouble(14);
                if(idCategory == 1)
                {
                    Category = "Столы и стулья";
                } else if(idCategory == 2){
                    Category = "Мягкая мебель";
                } else if(idCategory == 3){
                    Category = "Тумбы и комоды";
                }else if(idCategory == 4){
                    Category = "Кухни";
                }else if(idCategory == 5){
                    Category = "Офисная мебель";
                }else if(idCategory == 6){
                    Category = "Шкафы";
                }else if(idCategory == 7){
                    Category = "Гостинные";
                }else if(idCategory == 8){
                    Category = "Другое";
                }
                ShowForSearch furniture = new ShowForSearch();
                furniture.setId(id);
                furniture.setName(name);
                furniture.setDescription(shortdescription);
                furniture.setCategory(Category);
                furniture.setAdditionalInformation(information);
                furniture.setMaterial(material);
                furniture.setColor(color);
                furniture.setPrice(cost);
                furniture.setKlSklad(klSklad);
                furniture.setKlBuy(klBuy);
                furniture.setCountry(country);
                furniture.setGross(gross);
                furniture.setSelling(selling);
                furniture.setManagement(management);
                list.listOfSearch.add(furniture);

                System.out.println(" ID: " + id + " Name: " + name + " ShortDescrription: " + shortdescription + " Category: " + Category
                        + " Information: " + information + " Material: " + material + " Color: " + color + " Cost: " + cost + " KlSklad: " + klSklad + " KlBuy: " + klBuy + " Country: " + country
                        + " Gross: " + gross + " Selling: " + selling + " Management: " + management);
            }
        } catch(SQLException e){
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return list;
    }

    public ListOfSearch Search3(int type,double parametr) {
        ListOfSearch list = new ListOfSearch();
        System.out.println("Фильтрация вида " + type + ":");
        String select = "SELECT furniture.idFurniture, furniture.Name_furniture, furniture.ShortDefinitionFurniture, furniture.idCategory, " +
                    "specifications.additionalInformation, specifications.material, specifications.color, specifications.cost, specifications.klScklad, specifications.klProdano, specifications.producingCountry, " +
                    "dataforprofit.grossProfit, dataforprofit.sellingExpenses, dataforprofit.managementExpenses" +
                    " FROM furniture" +
                    " INNER JOIN specifications ON furniture.idFurniture = specifications.idFurniture" +
                    " INNER JOIN dataforprofit ON furniture.idFurniture = dataforprofit.idFurniture " +
                    " WHERE specifications.cost = " + parametr;
        try {
            PreparedStatement prSt = getDbConnections().prepareStatement(select);
            ResultSet rs = prSt.executeQuery(select);
            int id = 0;
            String name;
            String shortdescription;
            int idCategory;
            String Category = null;
            double cost;
            int klSklad;
            int klBuy;
            String material;
            String color;
            String country;
            String information;
            double gross;
            double selling;
            double management;
            while (rs.next()) {
                id = rs.getInt(1);
                name = rs.getString(2);
                shortdescription = rs.getString(3);
                idCategory = rs.getInt(4);
                information = rs.getString(5);
                material = rs.getString(6);
                color = rs.getString(7);
                cost = rs.getDouble(8);
                klSklad = rs.getInt(9);
                klBuy = rs.getInt(10);
                country = rs.getString(11);
                gross = rs.getDouble(12);
                selling = rs.getDouble(13);
                management = rs.getDouble(14);
                if(idCategory == 1)
                {
                    Category = "Столы и стулья";
                } else if(idCategory == 2){
                    Category = "Мягкая мебель";
                } else if(idCategory == 3){
                    Category = "Тумбы и комоды";
                }else if(idCategory == 4){
                    Category = "Кухни";
                }else if(idCategory == 5){
                    Category = "Офисная мебель";
                }else if(idCategory == 6){
                    Category = "Шкафы";
                }else if(idCategory == 7){
                    Category = "Гостинные";
                }else if(idCategory == 8){
                    Category = "Другое";
                }
                ShowForSearch furniture = new ShowForSearch();
                furniture.setId(id);
                furniture.setName(name);
                furniture.setDescription(shortdescription);
                furniture.setCategory(Category);
                furniture.setAdditionalInformation(information);
                furniture.setMaterial(material);
                furniture.setColor(color);
                furniture.setPrice(cost);
                furniture.setKlSklad(klSklad);
                furniture.setKlBuy(klBuy);
                furniture.setCountry(country);
                furniture.setGross(gross);
                furniture.setSelling(selling);
                furniture.setManagement(management);
                list.listOfSearch.add(furniture);

                System.out.println(" ID: " + id + " Name: " + name + " ShortDescrription: " + shortdescription + " Category: " + Category
                        + " Information: " + information + " Material: " + material + " Color: " + color + " Cost: " + cost + " KlSklad: " + klSklad + " KlBuy: " + klBuy + " Country: " + country
                        + " Gross: " + gross + " Selling: " + selling + " Management: " + management);
            }
        } catch(SQLException e){
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return list;
    }

    //УДАЛЕНИЕ ТОВАРА
    public void DelProduct(int id) throws SQLException, ClassNotFoundException {
        String select = "DELETE " +
                        "FROM furniture " +
                        "WHERE furniture.idFurniture = " + id;
        try {
            PreparedStatement prSt = getDbConnections().prepareStatement(select);
            prSt.executeUpdate();
        } catch(SQLException e){
            e.printStackTrace();
        }
    }




    /*РЕДАКТИРОВАНИЕ*/
    //ДОБАВЛЕНИЕ В ЧЕРНЫЙ СПИСОК
    public void ReductProduct1(int type, int id, String parametr) throws SQLException, ClassNotFoundException {
        String select ="";
        int category = 0;
        if(parametr.equals("Столы и стулья")){category = 1;}
        else if(parametr.equals("Мягкая мебель")){category = 2;}
        else if(parametr.equals("Тумбы и комоды")){category = 3;}
        else if(parametr.equals("Кухни")){category = 4;}
        else if(parametr.equals("Офисная мебель")){category = 5;}
        else if(parametr.equals("Шкафы")){category = 6;}
        else if(parametr.equals("Гостинные")){category = 7;}
        else if(parametr.equals("Другое")){category = 8;}

        if(type == 1){
            select = "UPDATE furniture " +
                     "SET furniture.idCategory = " + category + " " +
                     "WHERE furniture.idFurniture = " + id;
        } else if(type == 2){
            select = "UPDATE furniture " +
                    "SET furniture.Name_furniture = \'" + parametr + "\' " +
                    "WHERE furniture.idFurniture = " + id;
        } else if(type == 3){
            select = "UPDATE furniture " +
                     "SET furniture.ShortDefinitionFurniture = \'" + parametr + "\' " +
                     "WHERE furniture.idFurniture = " + id;
        } else if(type == 4){
            select = "UPDATE  specifications " +
                     "SET specifications.material = \'" + parametr + "\' " +
                    "WHERE specifications.idFurniture = " + id;
        }else if(type == 5){
            select = "UPDATE  specifications " +
                     "SET specifications.color = \'" + parametr + "\' " +
                     "WHERE specifications.idFurniture = " + id;
        }else if(type == 6){
            select = "UPDATE  specifications " +
                     "SET specifications.producingCountry = \'" + parametr + "\' " +
                     "WHERE specifications.idFurniture = " + id;
        }

        try {
            PreparedStatement prSt = getDbConnections().prepareStatement(select);
            prSt.executeUpdate();
        } catch(SQLException e){
            e.printStackTrace();
        }
    }

    public void ReductProduct2(int type, int id, double parametr) throws SQLException, ClassNotFoundException {
        String select ="";

        if(type == 7){
            select = "UPDATE specifications " +
                     "SET specifications.cost = " + parametr + " " +
                     "WHERE specifications.idFurniture = " + id;
        } else if(type == 10){
            select = "UPDATE dataforprofit " +
                     "SET dataforprofit.grossProfit = " + parametr + " " +
                     "WHERE dataforprofit.idFurniture = " + id;
        } else if(type == 11){
            select = "UPDATE dataforprofit " +
                     "SET dataforprofit.sellingExpenses = " + parametr + " " +
                     "WHERE dataforprofit.idFurniture = " + id;
        } else if(type == 12){
            select = "UPDATE dataforprofit " +
                     "SET dataforprofit.managementExpenses= " + parametr + " " +
                     "WHERE dataforprofit.idFurniture = " + id;
        }
        try {
            PreparedStatement prSt = getDbConnections().prepareStatement(select);
            prSt.executeUpdate();
        } catch(SQLException e){
            e.printStackTrace();
        }
    }

    public void ReductProduct3(int type, int id, int parametr) throws SQLException, ClassNotFoundException {
        String select ="";

        if(type == 8){
            select = "UPDATE specifications " +
                     "SET specifications.klScklad = " + parametr + " " +
                     "WHERE specifications.idFurniture = " + id;
        } else if(type == 9){
            select = "UPDATE specifications " +
                     "SET specifications.klProdano = " + parametr + " " +
                     "WHERE specifications.idFurniture = " + id;
        }
        try {
            PreparedStatement prSt = getDbConnections().prepareStatement(select);
            prSt.executeUpdate();
        } catch(SQLException e){
            e.printStackTrace();
        }
    }
}
