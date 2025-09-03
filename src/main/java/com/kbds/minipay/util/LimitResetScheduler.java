package com.kbds.minipay.util;

import java.time.LocalDate;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.kbds.minipay.domain.UserChargeLimit;
import com.kbds.minipay.repository.UserChargeLimitRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class LimitResetScheduler {

	private final UserChargeLimitRepository userChargeLimitRepository;

	@Scheduled(cron = "0 0 0 * * *")
	public void run() {
		List<UserChargeLimit> removeUserChargeLimitList = userChargeLimitRepository.findAllByChargeDateBefore(
			LocalDate.now());
		userChargeLimitRepository.deleteAll(removeUserChargeLimitList);
	}
}
