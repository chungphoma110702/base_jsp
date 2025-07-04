package com.example.base_jsp.models.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.Map;

@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public abstract class BaseDto {
	@Schema(description = "ID")
	private Long id;

	@Schema(description = "Thời gian tạo")
	private LocalDateTime createdAt;

	@Schema(description = "Thời gian cập nhật")
	private LocalDateTime updatedAt;

	@Schema(description = "Thời gian xóa")
	private LocalDateTime deletedAt;

	@Schema(description = "Người tạo")
	private String createdBy;

	@Schema(description = "Người chỉnh sửa")
	private String updatedBy;

	@Schema(description = "Người xóa")
	private String deletedBy;

	@Schema(description = "Dữ liệu động")
	private Map<String, Object> metaData;
}
