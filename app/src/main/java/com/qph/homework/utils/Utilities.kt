package com.qph.homework.utils

import android.annotation.SuppressLint
import com.qph.homework.Waybill
import com.qph.homework.manager.UserDatabaseHelper
import com.qph.homework.view.WaybillDisplayActivity
import org.json.JSONObject
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.StringReader
import java.net.HttpURLConnection
import java.net.URL
import kotlin.concurrent.thread

object Utilities {

    private fun readStringFromUrl(urlString: String): String {
        var connection: HttpURLConnection? = null
        val response = StringBuilder()
        try {

            val url = URL(urlString)
            connection = url.openConnection() as HttpURLConnection
            connection.connectTimeout = 8000
            connection.readTimeout = 8000

            val input = connection.inputStream
            val reader = BufferedReader(InputStreamReader(input))
            reader.use {
                reader.forEachLine {
                    response.append(it)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            connection?.disconnect()
        }
        return response.toString()
    }

    private fun parseXmlToList(xmlData: String): ArrayList<Waybill> {
        val xmlPullParser = XmlPullParserFactory.newInstance().newPullParser()
        xmlPullParser.setInput(StringReader(xmlData))

        var eventType = xmlPullParser.eventType
        val waybills: ArrayList<Waybill> = arrayListOf()
        val readingWaybill = Waybill()

        while (eventType != XmlPullParser.END_DOCUMENT) {
            val nodeName = xmlPullParser.name
            when (eventType) {
                XmlPullParser.START_TAG -> {
                    when (nodeName) {
                        "waybillNo" -> readingWaybill.waybillNumber = xmlPullParser.nextText()
                        "consignor" -> readingWaybill.consignor = xmlPullParser.nextText()
                        "consignorPhoneNumber" -> readingWaybill.consignorPhoneNumber =
                            xmlPullParser.nextText()
                        "consignee" -> readingWaybill.consignee = xmlPullParser.nextText()
                        "consigneePhoneNumber" -> readingWaybill.consigneePhoneNumber =
                            xmlPullParser.nextText()
                        "transportationDepartureStation" -> readingWaybill.transportationDepartureStation =
                            xmlPullParser.nextText()
                        "transportationArrivalStation" -> readingWaybill.transportationArrivalStation =
                            xmlPullParser.nextText()
                        "goodsDistributionAddress" -> readingWaybill.goodsDistributionAddress =
                            xmlPullParser.nextText()
                        "goodsName" -> readingWaybill.goodsName = xmlPullParser.nextText()
                        "numberOfPackages" -> readingWaybill.numberOfPackages =
                            xmlPullParser.nextText().toInt()
                        "freightPaidByTheReceivingParty" -> readingWaybill.freightPaidByTheReceivingParty =
                            xmlPullParser.nextText().toInt()
                        "freightPaidByConsignor" -> readingWaybill.freightPaidByConsignor =
                            xmlPullParser.nextText().toInt()
                    }
                }
                XmlPullParser.END_TAG -> {
                    if ("waybillRecord" == nodeName) {
                        waybills.add(readingWaybill.copy())
                    }
                }
            }
            eventType = xmlPullParser.next()
        }

        return waybills
    }

    private fun parseJsonToList(jsonData: String): ArrayList<Waybill> {
        val readingWaybill = Waybill()
        val waybills: ArrayList<Waybill> = arrayListOf()
        try {
            val jsonAllData = JSONObject(jsonData)
            val jsonArray = jsonAllData.getJSONArray("waybillRecord")

            for (i in 0 until jsonArray.length()) {
                val waybillJsonObject = jsonArray.getJSONObject(i)
                readingWaybill.waybillNumber = waybillJsonObject.getString("waybillNo")
                readingWaybill.consignor = waybillJsonObject.getString("consignor")
                readingWaybill.consignorPhoneNumber =
                    waybillJsonObject.getString(("consignorPhoneNumber"))
                readingWaybill.consignee = waybillJsonObject.getString("consignee")
                readingWaybill.consigneePhoneNumber =
                    waybillJsonObject.getString("consigneePhoneNumber")
                readingWaybill.transportationDepartureStation =
                    waybillJsonObject.getString("transportationDepartureStation")
                readingWaybill.transportationArrivalStation =
                    waybillJsonObject.getString("transportationArrivalStation")
                readingWaybill.goodsDistributionAddress =
                    waybillJsonObject.getString("goodsDistributionAddress")
                readingWaybill.goodsName = waybillJsonObject.getString("goodsName")
                readingWaybill.numberOfPackages = waybillJsonObject.getInt("numberOfPackages")
                readingWaybill.freightPaidByTheReceivingParty =
                    waybillJsonObject.getInt("freightPaidByTheReceivingParty")
                readingWaybill.freightPaidByConsignor =
                    waybillJsonObject.getInt("freightPaidByConsignor")

                waybills.add(readingWaybill.copy())
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return waybills
    }

    @SuppressLint("Range")
    private fun readWaybillsFromDatabase(databaseHelper: UserDatabaseHelper): ArrayList<Waybill> {
        val waybills: ArrayList<Waybill> = arrayListOf()

        val readingWaybill = Waybill()
        val db = databaseHelper.readableDatabase
        val cursor = db.rawQuery("select * from Waybill", null)
        if (cursor.moveToFirst()) {
            do {
                readingWaybill.waybillNumber =
                    cursor.getInt(cursor.getColumnIndex("waybill_number")).toString()
                readingWaybill.consignor = cursor.getString(cursor.getColumnIndex("consignor"))
                readingWaybill.consignorPhoneNumber =
                    cursor.getString(cursor.getColumnIndex("consignor_phone_number"))
                readingWaybill.consignee = cursor.getString(cursor.getColumnIndex("consignee"))
                readingWaybill.consigneePhoneNumber =
                    cursor.getString(cursor.getColumnIndex("consignee_phone_number"))
                readingWaybill.transportationDepartureStation =
                    cursor.getString(cursor.getColumnIndex("transportation_departure_station"))
                readingWaybill.transportationArrivalStation =
                    cursor.getString(cursor.getColumnIndex("transportation_arrival_station"))
                readingWaybill.goodsDistributionAddress =
                    cursor.getString(cursor.getColumnIndex("goods_distribution_address"))
                readingWaybill.goodsName = cursor.getString(cursor.getColumnIndex("goods_name"))
                readingWaybill.numberOfPackages =
                    cursor.getInt(cursor.getColumnIndex("number_of_packages"))
                readingWaybill.freightPaidByTheReceivingParty =
                    cursor.getInt(cursor.getColumnIndex("freight_paid_by_the_receiving_party"))
                readingWaybill.freightPaidByConsignor =
                    cursor.getInt(cursor.getColumnIndex("freight_paid_by_consignor"))

                waybills.add(readingWaybill.copy())
            } while (cursor.moveToNext())
        }
        cursor.close()

        return waybills
    }

    fun updateWaybillFromLocal(databaseHelper: UserDatabaseHelper, activity: WaybillDisplayActivity) {
        thread {
            val waybills = readWaybillsFromDatabase(databaseHelper)
            activity.updateWaybillList(waybills)
        }
    }

    fun updateWaybillFromHttpXml(urlString: String, activity: WaybillDisplayActivity) {
        thread {
            val response = readStringFromUrl(urlString)
            val waybills = parseXmlToList(response)
            activity.updateWaybillList(waybills)
        }
    }

    fun updateWaybillFromHttpJson(urlString: String, activity: WaybillDisplayActivity) {
        thread {
            val response = readStringFromUrl(urlString)
            val waybills = parseJsonToList(response)
            activity.updateWaybillList(waybills)
        }
    }
}