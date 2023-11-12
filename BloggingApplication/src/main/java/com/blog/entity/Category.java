package com.blog.entity;

import java.util.ArrayList;
import java.util.List;

import javax.management.loading.PrivateClassLoader;
import javax.print.event.PrintJobAttributeEvent;

import org.yaml.snakeyaml.composer.Composer;

import io.micrometer.common.lang.Nullable;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Entity
@Table(name = "Category")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Category {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="ID")
	private int categoryId;
	
	@Column(nullable = false, length = 30)
	private String categoryTitle;
	
	@Column(nullable = false)
	private String categoryDescription;

	@OneToMany(mappedBy = "category", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Post> posts = new ArrayList<>();
	
}
