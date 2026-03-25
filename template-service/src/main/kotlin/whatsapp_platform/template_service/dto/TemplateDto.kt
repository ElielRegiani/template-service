package whatsapp_platform.template_service.dto

import java.time.Instant

data class TemplateDto(
    val id: Long,
    val name: String,
    val content: String,
    val version: Int,
    val createdAt: Instant
)
