package com.magicwand.entity;

import com.magicwand.enums.ArticleType;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "magic_providers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MagicProvider {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ArticleType type;

    @OneToMany(mappedBy = "provider", fetch = FetchType.LAZY)
    private List<MagicArticle> articles;
}
