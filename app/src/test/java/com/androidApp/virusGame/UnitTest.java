package com.androidApp.virusGame;

import com.androidApp.virusGame.Model.Player;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class UnitTest {
    private String name = "Eugene";
    private String pw = "1234@";
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void singlePlayerTest(){
        Player p = new Player(name,pw);
        String result = p.getName();
        assertEquals(name,result);
    }

    @Test
    public void updatePlayerNameTest(){
        Player p = new Player(name,pw);
        String nName = "Jackson";
        p.setName(nName);
        assertEquals(nName,p.getName());
    }

    @Test
    public void updatePlayerPwTest(){
        Player p = new Player(name,pw);
        String nPw = "221.=9";
        p.setPassword(nPw);
        assertEquals(nPw,p.getPassword());
    }

}