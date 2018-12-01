package sample.Database;

public class Const {

    //ТАБЛИЦА USERS
    public static final String USER_TABLE = "users";
    public static final String USERS_ID = "idusers";
    public static final String USERS_USERNAME = "usersname";
    public static final String USERS_USERSURNAME = "usersurname";
    public static final String USERS_EMAIL = "email";
    public static final String USERS_TELEPHONE = "telephonenumber";
    public static final String USERS_LOGIN = "login";
    public static final String USERS_PASSWORD = "password";
    public static final String USERS_STATUS = "status";


    /*5 СВЯЗАННЫХ ТАБЛИЦ*/

    //ТАБЛИЦА CATEGORY
    public static final String CATEGORY_TABLE = "category";
    public static final String CATEGORY_ID = "idCategory";
    public static final String CATEGORY_NAME = "NameCategory";

    //ТАБЛИЦА FURNITURE
    public static final String FURNITURE_TABLE = "furniture";
    public static final String FURNITURE_ID = "idFurniture";
    public static final String FURNITURE_NAME = "Name_furniture";
    public static final String FURNITURE_DEFINITION = "ShortDefinitionFurniture";
    public static final String FURNITURE_ID_CATEGORY = "idCategory";

    //ТАБЛИЦА SPECIFICATIONS
    public static final String SPECIFICATIONS_TABLE = "specifications";
    public static final String SPECIFICATIONS_ID = "idspecifications";
    public static final String SPECIFICATIONS_ADDITIONAL = "additionalInformation";
    public static final String SPECIFICATIONS_MATERIAL = "material";
    public static final String SPECIFICATIONS_COLOR = "color";
    public static final String SPECIFICATIONS_COST = "cost";
    public static final String SPECIFICATIONS_COUNTRY = "producingCountry";
    public static final String SPECIFICATIONS_KLSKLAD = "klScklad";
    public static final String SPECIFICATIONS_FURNITURE_ID = "idFurniture";

    //ТАБЛИЦА DATAPROFIT
    public static final String DATAPROFIT_TABLE = "dataforprofit";
    public static final String DATAPROFIT_ID = "iddataForProfit";
    public static final String DATAPROFIT_GROSS = "grossProfit";
    public static final String DATAPROFIT_SELLING = "sellingExpenses";
    public static final String DATAPROFIT_MANAGEMENT = "managementExpenses";
    public static final String DATAPROFIT_FURNITURE_ID = "idFurniture";

    //ТАБЛИЦА PROFITRESULTS
    public static final String PROFITRESULTS_TABLE = "profitresults";
    public static final String PROFITRESULTS_ID = "idprofitResults";
    public static final String PROFITRESULTS_RESULT = "result";
    public static final String PROFITRESULTS_DATAPROFIT_ID = "iddataForProfit";
    public static final String DATAPROFIT_DATAFORROFIT_ID = "iddataForProfit";
}
