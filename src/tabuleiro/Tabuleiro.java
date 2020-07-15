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

	public void setColunas(Integer colunas) {
		this.colunas = colunas;
	}
	
	
	public Peca obterPeca(Integer linha, Integer coluna) {
		if(!isPosicaoExistente(linha, coluna)) {
			throw new TabuleiroException("Posição inexistente no tabuleiro!");
		}
		return pecas[linha][coluna];
	}
	
	public Peca obterPeca(Posicao posicao) {
		if(!isPosicaoExistente(posicao)) {
			throw new TabuleiroException("Posição inexistente no tabuleiro!");
		}
		return pecas[posicao.getLinha()][posicao.getColuna()];
	}
	
	public void colocarPeca(Peca peca, Posicao posicao) {
		if(temUmaPecaNaPosicao(posicao)) {
			throw new TabuleiroException("Já existe uma peça na posição " + posicao);
		}
		pecas[posicao.getLinha()][posicao.getColuna()] = peca;
		peca.posicaoMatriz = posicao;
	}
	
	public Boolean isPosicaoExistente(Integer linha, Integer coluna) {
		return linha >= 0 && linha < linhas && coluna >= 0 && coluna < colunas;
	}
	
	public Boolean isPosicaoExistente(Posicao posicao) {
		return isPosicaoExistente(posicao.getLinha(), posicao.getColuna());
	}

	public Boolean temUmaPecaNaPosicao(Posicao posicao) {
		if(!isPosicaoExistente(posicao)) {
			throw new TabuleiroException("Posição inexistente no tabuleiro!");
		}
		return obterPeca(posicao) != null;
	}
}
