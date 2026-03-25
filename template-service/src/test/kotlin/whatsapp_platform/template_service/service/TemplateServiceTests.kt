package whatsapp_platform.template_service.service

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.Import
import whatsapp_platform.template_service.dto.CreateTemplateRequest
import whatsapp_platform.template_service.exception.MissingVariableException

@DataJpaTest
@Import(TemplateService::class)
class TemplateServiceTests(@Autowired private val service: TemplateService) {

    @Test
    fun `create and render happy path`() {
        val req = CreateTemplateRequest(name = "hello", content = "Olá {{nome}}, seu pedido {{id}} foi enviado")
        val created = service.create(req)
        val rendered = service.render(created.id, mapOf("nome" to "Eliel", "id" to "123"))
        assertThat(rendered).isEqualTo("Olá Eliel, seu pedido 123 foi enviado")
    }

    @Test
    fun `render missing variable throws`() {
        val req = CreateTemplateRequest(name = "hello", content = "Olá {{nome}} {{sobrenome}}")
        val created = service.create(req)
        val ex = assertThrows(MissingVariableException::class.java) {
            service.render(created.id, mapOf("nome" to "Eliel"))
        }
        assertThat(ex.missing).containsExactly("sobrenome")
    }
}
