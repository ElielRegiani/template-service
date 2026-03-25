package whatsapp_platform.template_service.service

import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import whatsapp_platform.template_service.domain.TemplateEntity
import whatsapp_platform.template_service.dto.CreateTemplateRequest
import whatsapp_platform.template_service.dto.TemplateDto
import whatsapp_platform.template_service.exception.MissingVariableException
import whatsapp_platform.template_service.exception.NotFoundException
import whatsapp_platform.template_service.repository.TemplateRepository
import java.util.regex.Pattern

@Service
class TemplateService(private val repository: TemplateRepository) {

    private val placeholderPattern: Pattern = Pattern.compile("\\{\\{([a-zA-Z0-9_]+)\\}\\}")

    fun toDto(entity: TemplateEntity): TemplateDto = TemplateDto(
        id = entity.id ?: throw IllegalStateException("id is null"),
        name = entity.name,
        content = entity.content,
        version = entity.version,
        createdAt = entity.createdAt
    )

    @Transactional
    @CacheEvict(value = ["templates"], allEntries = true)
    fun create(request: CreateTemplateRequest): TemplateDto {
        val entity = TemplateEntity(name = request.name, content = request.content)
        val saved = repository.save(entity)
        return toDto(saved)
    }

    @Cacheable(value = ["templates"], key = "#id")
    fun get(id: Long): TemplateDto {
        val entity = repository.findById(id).orElseThrow { NotFoundException("Template not found: $id") }
        return toDto(entity)
    }

    @Transactional
    @CacheEvict(value = ["templates"], key = "#id")
    fun update(id: Long, request: CreateTemplateRequest): TemplateDto {
        val entity = repository.findById(id).orElseThrow { NotFoundException("Template not found: $id") }
        entity.name = request.name
        entity.content = request.content
        entity.version = entity.version + 1
        val saved = repository.save(entity)
        return toDto(saved)
    }

    fun preview(id: Long): String {
        val entity = repository.findById(id).orElseThrow { NotFoundException("Template not found: $id") }
        return entity.content
    }

    fun render(id: Long, variables: Map<String, String>): String {
        val entity = repository.findById(id).orElseThrow { NotFoundException("Template not found: $id") }
        val content = entity.content
        val matcher = placeholderPattern.matcher(content)
        val placeholders = mutableSetOf<String>()
        while (matcher.find()) {
            placeholders.add(matcher.group(1))
        }
        val missing = placeholders.filterNot { variables.containsKey(it) }
        if (missing.isNotEmpty()) throw MissingVariableException(missing)
        var result = content
        placeholders.forEach { key ->
            val value = variables[key] ?: ""
            result = result.replace("{{${key}}}", value)
        }
        return result
    }
}
