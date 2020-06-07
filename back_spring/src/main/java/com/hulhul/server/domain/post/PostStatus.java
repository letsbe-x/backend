package com.hulhul.server.domain.post;
/**
 * 
 * @author Xxings
 *	POST 상태 
 *  PROCEED // 진행 중
 *	SOLVED // 해결
 *	EXPIRED // 기한 지남
 *	CANCLE // 취소됨
 */
public enum PostStatus {
	PROCEED // 진행 중
	, SOLVED // 해결
	, EXPIRED // 기한 지남
	, PRIVATE // 비공개
	, CANCLE // 취소됨
}
