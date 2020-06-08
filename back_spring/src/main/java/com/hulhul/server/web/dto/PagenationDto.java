package com.hulhul.server.web.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.hulhul.server.domain.anonymous.AnonymousStatus;
import com.hulhul.server.domain.post.PostStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PagenationDto {
	private int page;
	private int requiredSize;
	private int totalPage;
	
	List<PostResponseDto> post;

}
