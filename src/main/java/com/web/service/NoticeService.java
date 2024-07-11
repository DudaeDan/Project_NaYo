package com.web.service;

import com.web.domain.Notice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NoticeService {
    Page<Notice> findAllNotices(Pageable pageable);
    Notice findNoticeById(Long id);
}