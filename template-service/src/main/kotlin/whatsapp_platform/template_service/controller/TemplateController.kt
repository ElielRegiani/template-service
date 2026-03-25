package whatsapp_platform.template_service.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import whatsapp_platform.template_service.dto.CreateTemplateRequest
import whatsapp_platform.template_service.dto.RenderRequest
import whatsapp_platform.template_service.dto.TemplateDto
import whatsapp_platform.template_service.service.TemplateService

@RestController
@RequestMapping("/templates")
class TemplateController(private val service: TemplateService) {

    @PostMapping
    fun create(@RequestBody request: CreateTemplateRequest): ResponseEntity<TemplateDto> {
        val dto = service.create(request)
        return ResponseEntity.ok(dto)
    }

    @GetMapping("/{id}")
    fun get(@PathVariable id: Long): ResponseEntity<TemplateDto> {
        val dto = service.get(id)
        return ResponseEntity.ok(dto)
    }

    @PutMapping("/{id}")
    fun update(@PathVariable id: Long, @RequestBody request: CreateTemplateRequest): ResponseEntity<TemplateDto> {
        val dto = service.update(id, request)
        return ResponseEntity.ok(dto)
    }

    @PostMapping("/{id}/render")
    fun render(@PathVariable id: Long, @RequestBody request: RenderRequest): ResponseEntity<String> {
        val result = service.render(id, request.variables)
        return ResponseEntity.ok(result)
    }

    @GetMapping("/{id}/preview")
    fun preview(@PathVariable id: Long): ResponseEntity<String> {
        val preview = service.preview(id)
        return ResponseEntity.ok(preview)
    }
}
