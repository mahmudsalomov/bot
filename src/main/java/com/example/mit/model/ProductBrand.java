package com.example.mit.model;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "product_brand")
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class ProductBrand {
    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "temp_id")
    private Long tempId;

    @Column(name = "name")
    private String name;

    @Column(name = "slug")
    private String slug;

    @Column(name = "image")
    private String image;

    @Type(type = "jsonb")
    @Column(name = "home_page",columnDefinition = "jsonb")
    private String homePage;

    @Type(type = "jsonb")
    @Column(name = "meta_json_uz",columnDefinition = "jsonb")
    private String metaJsonUz;

    @Type(type = "jsonb")
    @Column(name = "meta_json_ru",columnDefinition = "jsonb")
    private String metaJsonRu;

    @Type(type = "jsonb")
    @Column(name = "meta_json_oz",columnDefinition = "jsonb")
    private String metaJsonOz;

    @Column(name = "created_at")
    private Long createdAt;

    @Column(name = "updated_at")
    private Long updatedAt;

    @Column(name = "official_link")
    private String officialLink;


}
