Everton Bonfim Gomes	20191902047-0

Visão Geral do Software:
O aplicativo desenvolvido no Android Studio tem como objetivo permitir que os usuários realizem o cadastro e a visualização de mercados em sua região. Ele oferece recursos de autenticação de usuários, cadastro de mercados com informações como localização, nome e foto, exibição dos detalhes de cada mercado cadastrado e visualização da localização no mapa.

Papéis e Usuários:

Usuário comum: É o usuário registrado no aplicativo que possui um login e senha. Ele pode visualizar a lista de mercados cadastrados, selecionar um mercado específico para visualizar suas informações e localização no mapa.

Administrador: É o usuário responsável pela gestão do aplicativo. Ele possui acesso adicional para cadastrar novos mercados na base de dados do aplicativo.

Requisitos Funcionais:

Registro de Usuário:
Entradas necessárias: Nome, telefone, login e senha.
Processamento: Armazenamento dos dados do usuário no banco de dados do aplicativo.
Saída: Confirmação de registro bem-sucedido.

Autenticação de Usuário:
Entradas necessárias: Login e senha.
Processamento: Verificação dos dados de login e senha no banco de dados do aplicativo.
Saída: Permissão de acesso às funcionalidades do aplicativo.

Cadastro de Mercado (somente para o Administrador):
Entradas necessárias: Localização, nome e foto do mercado.
Processamento: Armazenamento dos dados do mercado no banco de dados do aplicativo.
Saída: Confirmação de cadastro bem-sucedido.

Visualização da Lista de Mercados:
Processamento: Recuperação dos dados de todos os mercados cadastrados no banco de dados.
Saída: Lista de mercados com informações resumidas (por exemplo, nome e localização) para exibição ao usuário.

Visualização dos Detalhes do Mercado:
Entradas necessárias: Seleção de um mercado específico na lista.
Processamento: Recuperação dos dados completos do mercado selecionado no banco de dados.
Saída: Exibição das informações detalhadas do mercado (nome, localização, foto) ao usuário.

Visualização da Localização no Mapa:
Entradas necessárias: Mercado selecionado.
Processamento: Conversão da localização do mercado em coordenadas geográficas para exibição no mapa.
Saída: Exibição do mercado selecionado no mapa, usando as coordenadas geográficas.

Relatórios e Saídas do App:

Confirmação de registro bem-sucedido.
Confirmação de cadastro de mercado bem-sucedido.
Lista de mercados cadastrados.
Informações detalhadas de um mercado selecionado.
Exibição da localização do mercado no mapa.

