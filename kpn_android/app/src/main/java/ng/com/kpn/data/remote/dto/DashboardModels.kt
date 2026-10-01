package ng.com.kpn.data.remote.dto

import com.google.gson.annotations.SerializedName

data class SecretariatDashboardDto(
    @SerializedName("total_members") val totalMembers: Int,
    @SerializedName("verified_members") val verifiedMembers: Int,
    @SerializedName("active_meetings") val activeMeetings: Int,
    @SerializedName("pending_documents") val pendingDocuments: Int
)

data class FinanceDashboardDto(
    @SerializedName("total_income") val totalIncome: Long,
    @SerializedName("total_expenses") val totalExpenses: Long,
    @SerializedName("balance") val balance: Long,
    @SerializedName("pending_receipts") val pendingReceipts: Int
)

data class ZonalDashboardDto(
    @SerializedName("zone_name") val zoneName: String,
    @SerializedName("lgas_in_zone") val lgasInZone: Int,
    @SerializedName("zonal_members") val zonalMembers: Int,
    @SerializedName("documents_filed") val documentsFiled: Int,
    @SerializedName("zone_announcements") val zoneAnnouncements: Int
)

data class LgaDashboardDto(
    @SerializedName("lga_name") val lgaName: String,
    @SerializedName("lga_members") val lgaMembers: Int,
    @SerializedName("wards_active") val wardsActive: Int,
    @SerializedName("pending_approvals") val pendingApprovals: Int,
    @SerializedName("lga_balance") val lgaBalance: Long
)

data class WardDashboardDto(
    @SerializedName("ward_name") val wardName: String,
    @SerializedName("ward_members") val wardMembers: Int,
    @SerializedName("pending_approvals") val pendingApprovals: Int,
    @SerializedName("ward_balance") val wardBalance: Long,
    @SerializedName("notices_sent") val noticesSent: Int
)

data class MediaPipelineDto(
    @SerializedName("pending_review") val pendingReview: Int,
    @SerializedName("published_news") val publishedNews: Int,
    @SerializedName("active_campaigns") val activeCampaigns: Int,
    @SerializedName("trusted_reporters") val trustedReporters: Int
)

data class VPOversightDto(
    @SerializedName("zonal_directors_active") val zonalDirectorsActive: Int,
    @SerializedName("lga_coordinators_active") val lgaCoordinatorsActive: Int,
    @SerializedName("disciplinary_cases") val disciplinaryCases: Int,
    @SerializedName("operational_reports") val operationalReports: Int
)
