package com.qph.homework.utils

object ConstantsValue {
    const val localSource = 1
    const val xmlSource = 2
    const val jsonSource = 3

    const val xmlDataUrl = "http://60.12.122.142:6080/simulated-Waybills-db.xml"
    const val jsonDataUrl = "http://60.12.122.142:6080/simulated-Waybills-db.json"

    const val databaseFileName = "Users.db"
    const val databaseVersion = 1

    const val createUserSqliteSentence = "create table User (" +
            "user_id integer primary key autoincrement," +
            "user_department text," +
            "user_name text," +
            "user_login text," +
            "user_password text," +
            "user_tel text," +
            "departure_station text)"

    const val  createWaybillSqliteSentence = "create table Waybill (" +
            "waybill_number integer primary key autoincrement," +
            "consignor text," +
            "consignor_phone_number text," +
            "consignee text," +
            "consignee_phone_number text," +
            "transportation_departure_station text," +
            "transportation_arrival_station text," +
            "goods_distribution_address text," +
            "goods_name text," +
            "number_of_packages integer," +
            "freight_paid_by_the_receiving_party integer," +
            "freight_paid_by_consignor integer)"

    const val insertUserSentence =
        "insert into User (" +
                "user_department," +
                "user_name," +
                "user_login," +
                "user_password," +
                "user_tel," +
                "departure_station) values (?, ?, ?, ?, ?, ?)"

    const val insertWaybillSentence =
        "insert into Waybill (" +
                "consignor," +
                "consignor_phone_number," +
                "consignee," +
                "consignee_phone_number," +
                "transportation_departure_station," +
                "transportation_arrival_station," +
                "goods_distribution_address," +
                "goods_name," +
                "number_of_packages," +
                "freight_paid_by_the_receiving_party," +
                "freight_paid_by_consignor) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"

    const val unknownInformationText = "Unknown"
    const val unknownInformationInteger = "0"
}