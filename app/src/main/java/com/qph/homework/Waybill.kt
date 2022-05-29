package com.qph.homework

data class Waybill(
    var waybillNumber: String = "",
    var consignor: String = "",
    var consignorPhoneNumber: String = "",
    var consignee: String = "",
    var consigneePhoneNumber: String = "",
    var transportationDepartureStation: String = "",
    var transportationArrivalStation: String = "",
    var goodsDistributionAddress: String = "",
    var goodsName: String = "",
    var numberOfPackages: Int = 0,
    var freightPaidByTheReceivingParty: Int = 0,
    var freightPaidByConsignor: Int = 0
)
