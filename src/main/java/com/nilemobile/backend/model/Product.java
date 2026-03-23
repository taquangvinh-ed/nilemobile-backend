package com.nilemobile.backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "products")
public class Product extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(length = 36)
    private Long productId;

    @Column(name = "name", nullable = false)
    @NotBlank(message = "Tên sản phẩm không được để trống")
    @Size(max = 255, message = "Tên sản phẩm không được vượt quá 255 ký tự")
    private String name;

    @Column(name = "screenSize", length = 20)
    @Positive(message = "Kích thước màn hình phải là số dương")
    private Float screenSize;

    @Column(name = "displayTech")
    private String displayTech;

    @Column(name = "resolution", length = 200)
    private String resolution;

    @Column(name = "refreshRate", length = 200)
    private String refreshRate;

    @Column(name = "frontCamera", length = 200)
    private String frontCamera;

    @Column(name = "backCamera", length = 200)
    private String backCamera;

    @Column(name = "chipset", length = 200)
    private String chipset;

    @Column(name = "cpu", length = 200)
    private String cpu;

    @Column(name = "gpu", length = 200)
    private String gpu;

    @Column(name = "BatteryCapacity", length = 20)
    @Positive(message = "Dung lượng pin phải là số dương")
    private Integer batteryCapacity;

    @Column(name = "chargingPort", length = 200)
    private String chargingPort;

    @Column(name = "OS", length = 200)
    private String os;

    @Column(name = "productSize", length = 200)
    private String productSize;

    @Column(name = "productWeight", length = 20)
    private Float productWeight;

    @Column(name = "description", length = 1000)
    private String description;


    @ManyToOne
    @JoinColumn(name = "categories_id", nullable = false)
    private Categories categories;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<Variation> variations = new ArrayList<>();

    private LocalDateTime createAt;

}
