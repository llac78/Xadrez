package tabuleiro;

public class Peca {
	
	protected Posicao posicaoMatriz;
	private Tabuleiro tabuleiro;

	public Peca(Tabuleiro tabuleiro) {
		this.tabuleiro = tabuleiro;
	}

	public Tabuleiro getTabuleiro() {
		return tabuleiro;
	}
	
	
	

}
