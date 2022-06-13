package com.example.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.app.data.models.Categoria
import com.example.app.data.models.Produto
import com.example.app.data.models.Setor
import com.example.app.ui.theme.AppTheme
import com.example.app.views.categoria.*
import com.example.app.views.produto.*
import com.example.app.views.setor.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val setorViewModel: SetorViewModel by viewModels {
            SetorViewModelFactory(
                (this.applicationContext as AppApplication).appDatabase.setorDao()
            )
        }
        val categoriaViewModel: CategoriaViewModel by viewModels {
            CategoriaViewModelFactory(
                (this.applicationContext as AppApplication).appDatabase.categoriaDao()
            )
        }
        val produtoViewModel: ProdutoViewModel by viewModels {
            ProdutoViewModelFactory(
                (this.applicationContext as AppApplication).appDatabase.produtoDao()
            )
        }


        // INSERIR DADOS INICIAIS:
        /*
        val setorsList: List<Setor> = listOf(
            Setor(1,
                "Padaria",
                "Setor de Padaria"
            ),
            Setor(2,
                "Açougue",
                "Setor de Açougue"
            ),
            Setor(3,
                "Estoque",
                "Setor de Estoque"
            ),
            Setor(4,
                "Loja",
                "Setor de Loja"
            ),
            Setor(5,
                "Imobilizado",
                "Setor Imobilizado(Não Utilizar)"
            ),
            Setor(6,
                "Transporte",
                "Setor de Transporte"
            ),
        )

        val categoriasList: List<Categoria> = listOf(
            Categoria(1,
                "Bebidas",
                "Produtos Tipo Bebidas"
            ),
            Categoria(2,
                "Alimentos",
                "Produtos Tipo Alimentos"
            ),
            Categoria(3,
                "Verduras",
                "Produtos Tipo Verduras"
            ),
            Categoria(4,
                "Materiais",
                "Produtos Tipo Materiais"
            ),
            Categoria(5,
                "Carne",
                "Produtos Tipo Carne"
            ),
            Categoria(6,
                "Despesas",
                "Produtos Tipo Despesas"
            ),
        )

        val produtosList: List<Produto> = listOf(
            Produto(1,
                6,
                1,
                "7893",
                "Bandeja de Isopor"),
            Produto(2,
                6,
                1,
                "7894",
                "Copo Descartavel"),
            Produto(3,
                6,
                3,
                "7895",
                "Folha de Papel"),
            Produto(4,
                5,
                2,
                "7896",
                "Carne Moida"),
            Produto(5,
                2,
                2,
                "7897",
                "Tempero"),
            Produto(6,
                4,
                2,
                "7898",
                "Faca Para Carne"),
        )

        fun inserirDados(){
            categoriasList.forEach {
                categoriaViewModel.insert(it)
            }
            setorsList.forEach {
                setorViewModel.insert(it)
            }
            produtosList.forEach {
                produtoViewModel.insert(it)
            }
        }

        inserirDados() */

        setContent {
            AppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MyApp(
                        produtoViewModel,
                        setorViewModel,
                        categoriaViewModel,
                    )
                }
            }
        }
    }
}

@Composable
fun MyApp(
    produtoViewModel: ProdutoViewModel,
    setorViewModel: SetorViewModel,
    categoriaViewModel: CategoriaViewModel,
) {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            BottomAppBar (
                modifier = Modifier.height(80.dp),
                backgroundColor = Color.Gray
            ) {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                bottomNavScreens.forEach { botNavScreen ->
                    BottomNavigationItem(
                        icon = { Icon(
                            modifier = Modifier.size(50.dp),
                            painter = painterResource(id = botNavScreen.icon),
                            contentDescription = stringResource(id = botNavScreen.name)
                        )},
                        label = { Text(text = stringResource(id = botNavScreen.name))},
                        selected = currentDestination?.hierarchy?.any{
                            it.route == botNavScreen.route
                        } == true,
                        onClick = {
                            navController.navigate(botNavScreen.route){
                                popUpTo(navController.graph.startDestinationId){
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) {
        NavHost(
            modifier = Modifier.padding(it),
            navController = navController,
            startDestination = Screen.SetorScreen.route
        ){
            composable(Screen.ProdutoScreen.route){
                ProdutosScreen(navController, produtoViewModel)
            }
            composable(Screen.CategoriaScreen.route){
                CategoriasScreen(navController, categoriaViewModel)
            }
            composable(Screen.SetorScreen.route){
                SetorsScreen(navController, setorViewModel)
            }
            composable(
                route = "addeditproduto?produtoId={produtoId}",
                arguments = listOf(navArgument("produtoId"){
                    defaultValue = -1
                    type = NavType.IntType
                })
            ){ navBackStackEntry ->
                val id = navBackStackEntry.arguments?.getInt("produtoId") ?: -1

                val produto = getProduto(id, produtoViewModel)
                AddEditProdutoScreen(
                    navController,
                    categoriaViewModel,
                    setorViewModel,
                    produtoViewModel,
                    produto
                )
            }
            composable(
                route = "viewproduto?produtoId={produtoId}",
                arguments = listOf(navArgument("produtoId"){
                    defaultValue = -1
                    type = NavType.IntType
                })
            ){ navBackStackEntry ->
                val id = navBackStackEntry.arguments?.getInt("produtoId") ?: -1

                val produto = getProduto(id, produtoViewModel)
                val categoria = getCategoria(produto.produtoCategoria, categoriaViewModel)
                val setor = getSetor(produto.produtoSetor, setorViewModel)
                ProdutoViewScreen(
                    produto,
                    categoria,
                    setor
                )
            }
            composable(
                route = "viewcategoria?categoriaId={categoriaId}",
                arguments = listOf(navArgument("categoriaId"){
                    defaultValue = -1
                    type = NavType.IntType
                })
            ){ navBackStackEntry ->
                val id = navBackStackEntry.arguments?.getInt("categoriaId") ?: -1
                val categoria = getCategoria(id, categoriaViewModel)
                CategoriaViewScreen(
                    categoria
                )
            }
            composable(
                route = "viewsetor?setorId={setorId}",
                arguments = listOf(navArgument("setorId"){
                    defaultValue = -1
                    type = NavType.IntType
                })
            ){ navBackStackEntry ->
                val id = navBackStackEntry.arguments?.getInt("setorId") ?: -1
                val setor = getSetor(id, setorViewModel)
                SetorViewScreen(
                    setor
                )
            }
            composable(
                route = "addeditsetor?setorId={setorId}",
                arguments = listOf(navArgument("setorId"){
                    defaultValue = -1
                    type = NavType.IntType
                })
            ){ navBackStackEntry ->
                val id = navBackStackEntry.arguments?.getInt("setorId") ?: -1
                val setor = getSetor(id, setorViewModel)
                AddEditSetorScreen(
                    navController,
                    setorViewModel,
                    setor
                )
            }
            composable(
                route = "addeditcategoria?categoriaId={categoriaId}",
                arguments = listOf(navArgument("categoriaId"){
                    defaultValue = -1
                    type = NavType.IntType
                })
            ){ navBackStackEntry ->
                val id = navBackStackEntry.arguments?.getInt("categoriaId") ?: -1
                val categoria = getCategoria(id, categoriaViewModel)
                AddEditCategoriaScreen(
                    navController,
                    categoriaViewModel,
                    categoria
                )
            }
        }
    }
}

private val bottomNavScreens = listOf(
    Screen.SetorScreen,
    Screen.CategoriaScreen,
    Screen.ProdutoScreen
)

fun getSetor(id: Int?, setorViewModel: SetorViewModel): Setor {
    setorViewModel.allSetors.value?.forEach{
        if(id == it.setorId){
            return it
        }
    }
    return Setor(
        -1,
        "",
        ""
    )
}

fun getProduto(id: Int?, produtoViewModel: ProdutoViewModel): Produto {
    produtoViewModel.allProdutos.value?.forEach{
        if(id == it.produtoId){
            return it
        }
    }
    return Produto(
        -1,
        -1,
        -1,
        "",
        ""
    )
}

fun getCategoria(id: Int?, categoriaViewModel: CategoriaViewModel): Categoria {
    categoriaViewModel.allCategorias.value?.forEach{
        if(id == it.categoriaId){
            return it
        }
    }
    return Categoria(
        -1,
        "",
        ""
    )
}

sealed class Screen(
    val route: String,
    @DrawableRes val icon: Int,
    @StringRes val name: Int,
){
    object ProdutoScreen: Screen("produtolist", R.drawable.produto_icon, R.string.produto)
    object CategoriaScreen: Screen("categorialist", R.drawable.categoria_icon, R.string.categoria)
    object SetorScreen: Screen("setorlist", R.drawable.setor_icon, R.string.setor)
}
