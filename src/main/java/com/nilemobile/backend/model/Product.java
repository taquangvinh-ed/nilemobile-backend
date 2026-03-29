package com.nilemobile.backend.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "products")
public class Product extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    private String productName;

    @Type(JsonType.class)
    @Column(columnDefinition = "json")
    private Map<String, String> attributes;

    @ManyToOne
    @JoinColumn(name = "categoryId", nullable = false)
    private Categories categories;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<Variation> variations = new ArrayList<>();

}
