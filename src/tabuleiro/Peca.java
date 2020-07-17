package tabuleiro;

public abstract class Peca {
	
	protected Posicao posicaoMatriz;
	private Tabuleiro tabuleiro;

	public Peca(Tabuleiro tabuleiro) {
		this.tabuleiro = tabuleiro;
	}

	public Tabuleiro getTabuleiro() {
		return tabuleiro;
	}
	
	public abstract Boolean[][] movimentosPossiveis();
	
	public Boolean movimentoPossivel(Posicao posicao) {
		return movimentosPossiveis()[posicao.getLinha()][posicao.getColuna()];
	}
	
	public Boolean existeMovimentosPossiveis() {
		Boolean[][] matriz = movimentosPossiveis();
		
		for (int i = 0; i < matriz.length; i++) {
			for (int j = 0; j < matriz.length; j++) {
				if(matriz[i][j]) {
					return true;
				}
			}
		}
		return false;
	}

}
