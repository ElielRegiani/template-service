package whatsapp_platform.template_service.domain

import jakarta.persistence.*
import java.time.Instant

@Entity
@Table(name = "templates")
class TemplateEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    var name: String = "",

    @Lob
    @Column(columnDefinition = "text")
    var content: String = "",

    var version: Int = 1,

    @Column(name = "created_at", nullable = false, updatable = false)
    var createdAt: Instant = Instant.now()
) {
    // no-arg constructor for JPA handled by Kotlin defaults + allOpen
}
