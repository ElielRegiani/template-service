package whatsapp_platform.template_service.dto

data class RenderRequest(
    val variables: Map<String, String> = emptyMap()
)
