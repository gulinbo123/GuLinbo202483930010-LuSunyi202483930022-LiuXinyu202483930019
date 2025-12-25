package com.nuist.test.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.nuist.test.Service.PlayerService;
import com.nuist.test.Service.WorldService;
import com.nuist.test.Entity.PlayerTable;
import com.nuist.test.Entity.WorldTable;

import java.util.Set;

@CrossOrigin(origins = "http://localhost:5173") // ⭐⭐⭐ 关键：允许前端跨域
@RestController
public class PlayerController {

	@Autowired
	private PlayerService playerService;

	@Autowired
	private WorldService worldService;

	// 登录接口
	@GetMapping("/validate")
	public boolean login(
			@RequestParam String username,
			@RequestParam String password
	) {
		return playerService.loginService(username, password);
	}

	// 查询世界
	@GetMapping("/worlds")
	public Set<WorldTable> worlds(@RequestParam String pid) {
		return playerService.allWorlds(Integer.parseInt(pid));
	}

	// 查询玩家
	@GetMapping("/players")
	public Set<PlayerTable> players(@RequestParam String wid) {
		return worldService.allPlayers(Integer.parseInt(wid));
	}
}
