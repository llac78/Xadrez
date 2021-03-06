package tabuleiro;

public class Tabuleiro {
	
	private Integer linhas;
	private Integer colunas;
	private Peca[][] pecas;
	
	public Tabuleiro(Integer linhas, Integer colunas) {
		if(linhas < 1 || colunas < 1) {
			throw new TabuleiroException("Erro ao criar tabuleiro: deve haver, pelo menos, 1 linha e 1 coluna!");
		}
		this.linhas = linhas;
		this.colunas = colunas;
		pecas = new Peca[linhas][colunas];
	}

	public Integer getLinhas() {
		return linhas;
	}

	public Integer getColunas() {
		return colunas;
	}
	
	public Peca obterPeca(Integer linha, Integer coluna) {
		if(!isPosicaoExistente(linha, coluna)) {
			throw new TabuleiroException("Posi��o inexistente no tabuleiro!");
		}
		return pecas[linha][coluna];
	}
	
	public Peca obterPeca(Posicao posicao) {
		if(!isPosicaoExistente(posicao)) {
			throw new TabuleiroException("Posi��o inexistente no tabuleiro!");
		}
		return pecas[posicao.getLinha()][posicao.getColuna()];
	}
	
	public void colocarPeca(Peca peca, Posicao posicao) {
		if(temUmaPecaNaPosicao(posicao)) {
			throw new TabuleiroException("J� existe uma pe�a na posi��o " + posicao);
		}
		pecas[posicao.getLinha()][posicao.getColuna()] = peca;
		peca.posicaoMatriz = posicao;
	}
	
	public Peca removerPeca(Posicao posicao) {
		if(!isPosicaoExistente(posicao)) {
			throw new TabuleiroException("Posi��o inexistente no tabuleiro!");
		}
		if(obterPeca(posicao) == null) {
			return null;
		}
		Peca aux = obterPeca(posicao);
		aux.posicaoMatriz = null;
		pecas[posicao.getLinha()][posicao.getColuna()] = null;
		
		return aux;
	}
	
	public boolean isPosicaoExistente(Integer linha, Integer coluna) {
		return linha >= 0 && linha < linhas && coluna >= 0 && coluna < colunas;
	}
	
	public boolean isPosicaoExistente(Posicao posicao) {
		return isPosicaoExistente(posicao.getLinha(), posicao.getColuna());
	}

	public boolean temUmaPecaNaPosicao(Posicao posicao) {
		if(!isPosicaoExistente(posicao)) {
			throw new TabuleiroException("Posi��o inexistente no tabuleiro!");
		}
		return obterPeca(posicao) != null;
	}
}
