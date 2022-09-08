package br.com.GerenciamentoDeUsuarios.API.controller.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;

import br.com.GerenciamentoDeUsuarios.API.models.Annotation;

public class AnnotationDto {

  private Long annotationId;
  
  private Long user_id;

  private String content;

  private LocalDateTime createdAt;

  private LocalDateTime updatedAt;

  private UserDto user;

  public AnnotationDto(Annotation annotation) {
    this.annotationId = annotation.getUserAnnotationId();
    this.user_id = annotation.getUser_id();
    this.content = annotation.getContent();
    this.createdAt = annotation.getRegisteredAt();
    this.updatedAt = annotation.getUpdatedAt();
    this.user = UserDto.convert(annotation.getUser());
  }

  public Long getAnnotationId() {
    return this.annotationId;
  }

  public Long getUser_id() {
    return this.user_id;
  }

  public String getContent() {
    return this.content;
  }

  public LocalDateTime getCreatedAt() {
    return this.createdAt;
  }

  public LocalDateTime getUpdatedAt() {
    return this.updatedAt;
  }

  public UserDto getUser() {
    return this.user;
  }

  public static AnnotationDto convert(Annotation annotation) {
    return new AnnotationDto(annotation);
  }

  public static List<AnnotationDto> convert(List<Annotation> annotations) {
    final List<AnnotationDto> list = new ArrayList<>();
    
    annotations.forEach(annotation -> list.add(AnnotationDto.convert(annotation)));

    return list;
  }

  public static Page<AnnotationDto> convert(Page<Annotation> annotations) {
    return annotations.map(AnnotationDto::new);
  }
}
