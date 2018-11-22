package uet.oop.bomberman.entities.character.enemy.ai;

public class AILow extends AI {

	@Override
	public int calculateDirection() {
		// TODO: cai dat thuat toan tim duong di
                return random.nextInt(4);
	}

}
