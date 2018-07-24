package com.game.puzzle.service;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/mines")
@RestController
public class MineSweeperGameController extends GameController<MineSweeperGameService> {


}
