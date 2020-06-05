//package com.hulhul.server.web.controllerV2;
//
//import javax.servlet.http.HttpSession;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.hulhul.server.domain.post.Post;
//import com.hulhul.server.domain.talk.Talk;
//import com.hulhul.server.domain.user.User;
//import com.hulhul.server.web.dto.PostRequestDto;
//import com.hulhul.server.web.dto.TalkRequestDto;
//import com.hulhul.server.web.dto.TalkResponseDto;
//import com.hulhul.server.web.service.PostService;
//import com.hulhul.server.web.service.TalkService;
//import com.hulhul.server.web.util.HttpSessionUtils;
//
//@RestController
//@RequestMapping("/api/v1/talk")
//public class V2TalkController {
//
//	// TODO : Socket 또는 Webflux
//
//	// TODO : FCM ( Firebase Cloud Message)관련 추가
//	@Autowired
//	TalkService talkService;
//
//	@Autowired
//	PostService postService;
//
//	@GetMapping("{post_id}/list")
//	public ResponseEntity getTalk(@PathVariable Long post_id) {
//		Post post = postService.getPost(post_id);
//		if (post != null) {
//			return ResponseEntity.ok(talkService.getTalkList(post));
//		}
//		return new ResponseEntity(HttpStatus.NOT_FOUND);
//	}
//
//	// C
//	@PostMapping("write")
//	public ResponseEntity savePost(@RequestBody TalkRequestDto dto, HttpSession session) {
//		if (!HttpSessionUtils.isLoginUser(session)) {
//			return null;
//		}
//
//		User user = HttpSessionUtils.getUserFormSession(session);
//
//		return ResponseEntity.ok(talkService.writeTalk(dto, user));
//	}
//
//	// R
//	@Transactional(readOnly = true) // 데이터의 변경이 없는 읽기 전용 메서드에 사용, 속성 컨텍스트를 플러시 하지 않으므로 약간의 성능 향상(읽기 전용에는 다 적용)
//	@GetMapping("{talk_id}")
//	public ResponseEntity getPost(@PathVariable Long talk_id) {
//		Talk talk = talkService.getTalk(talk_id);
//
//		if (talk != null) {
//			return ResponseEntity.ok(getDto(talk));
//		}
//		return new ResponseEntity(HttpStatus.NOT_FOUND);
//	}
//
//	// U
//	@PutMapping("{talk_id}")
//	public ResponseEntity updatePost(@PathVariable Long talk_id, @RequestBody PostRequestDto dto, HttpSession session) {
//		if (talk_id == null) {
//			return new ResponseEntity(HttpStatus.NOT_MODIFIED);
//			// 잘못된 요청
//		}
//		User user = HttpSessionUtils.getUserFormSession(session);
//		Long user_id = user.getId();
//
//		return ResponseEntity.ok(getDto(talkService.updateTalk(talk_id, user_id, dto)));
//	}
//
//	// D
//	@DeleteMapping("{talk_id}")
//	public ResponseEntity deletePost(@PathVariable Long talk_id, HttpSession session) {
//		if (talk_id == null) {
//			return new ResponseEntity(HttpStatus.NOT_MODIFIED);
//			// 잘못된 요청
//		}
//		User user = HttpSessionUtils.getUserFormSession(session);
//		Long user_id = user.getId();
//
//		return ResponseEntity.ok(talkService.deleteTalk(talk_id, user_id));
//	}
//
//	public TalkResponseDto getDto(Talk talk) {
//		return TalkResponseDto.builder().talk(talk).build();
//	}
//}
