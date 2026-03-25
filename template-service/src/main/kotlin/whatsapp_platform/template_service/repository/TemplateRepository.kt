package whatsapp_platform.template_service.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import whatsapp_platform.template_service.domain.TemplateEntity

@Repository
interface TemplateRepository : JpaRepository<TemplateEntity, Long> {
    fun findByName(name: String): TemplateEntity?
}
