package com.hulhul.server.web.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hulhul.server.domain.post.Post;
import com.hulhul.server.domain.talk.Talk;
import com.hulhul.server.domain.talk.TalkRepo;
import com.hulhul.server.domain.user.User;
import com.hulhul.server.web.dto.PostRequestDto;
import com.hulhul.server.web.dto.TalkRequestDto;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true) // 데이터의 변경이 없는 읽기 전용 메서드에 사용, 속성 컨텍스트를 플러시 하지 않으므로 약간의 성능 향상(읽기 전용에는 다 적용)
@RequiredArgsConstructor // final, @NonNull이 붙은 필드를 파라미터로 받는 생성자를 만들어주는 에너테이션
public class TalkService {

	// 객체를 필드주입이 아닌 생성자주입으로 넣는것이 좋다.
	private final TalkRepo talkRepo;

	public List<Talk> getTalkList(Post post) {
		return talkRepo.findByPost(post);
	}

	public Talk getTalk(Long talk_id) {
		return talkRepo.findById(talk_id).get();
	}

	@Transactional
	public Talk writeTalk(TalkRequestDto talkDto, User user) {
		Talk talk = talkDto.toEntity(user);

		return talkRepo.save(talk);
	}

	@Transactional
	public Talk updateTalk(Long talk_id, Long user_id, PostRequestDto postDto) {
		Talk talk = talkRepo.findById(talk_id).get();
		User originUser = talk.getUser();

		if (doMatchUser(originUser, user_id)) {
			return null;
			// 권한 없음
		}

		talk.setUpdate(talk.getContents(), talk.getAnonymous());

		return talk;
	}

	@Transactional
	public boolean deleteTalk(Long talk_id, Long user_id) {
		Talk talk = getTalk(talk_id);
		User originUser = talk.getUser();

		if (doMatchUser(originUser, user_id)) {
			return false;
			// 권한 없음
		}

		talkRepo.delete(talk);
		return true;
	}

	public boolean doMatchUser(User user, Long user_id) {
		return !user.getId().equals(user_id);
	}
}
