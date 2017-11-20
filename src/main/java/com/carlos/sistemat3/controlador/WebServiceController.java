package com.carlos.sistemat3.controlador;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.carlos.sistemat3.modelo.Response;
import com.carlos.sistemat3.modelo.Identificador;
import com.carlos.sistemat3.entidad.User;
import com.carlos.sistemat3.entidad.Producto;
import com.carlos.sistemat3.entidad.Cliente;
import com.carlos.sistemat3.entidad.Proveedor;
import com.carlos.sistemat3.entidad.EstadoTabla;
import com.carlos.sistemat3.servicio.UsuarioServicio;
import com.carlos.sistemat3.servicio.ClienteServicio;
import com.carlos.sistemat3.servicio.ProductoServicio;
import com.carlos.sistemat3.servicio.ProveedorServicio;
import com.carlos.sistemat3.servicio.EstadoTablaServicio; 

@RestController
public class WebServiceController {
	private final Log LOG= LogFactory.getLog(WebServiceController.class); 
	
	//servicios
	@Autowired
	@Qualifier("usuarioServicio")
	private UsuarioServicio usuarioServicio;
	
	@Autowired
	@Qualifier("productoServicio")
	private ProductoServicio productoServicio;
	
	
	@Autowired
	@Qualifier("clienteServicio")
	private ClienteServicio clienteServicio;

	@Autowired
	@Qualifier("proveedorServicio")
	private ProveedorServicio proveedorServicio;
	
	
	@Autowired
	@Qualifier("estadoTablaServicio")
	private EstadoTablaServicio estadoTablaServicio;
	
	/*Api Usuarios*/
	
	/**
	 * Obtener todos los usuarios
	 * @return listado de usuarios
	 * */
	@GetMapping("/usuarios")
	public Response getUsuarios() {		
		return new Response<User>(Response.STATUS_OK, usuarioServicio.all());
	}
	
	
	/**
	 * Ingresar un nuevo usuario
	 * @return status si ha sido exitoso o no
	 * */
	@PostMapping("/usuarios")
	public Response addUsuario(@RequestParam(value="username",defaultValue="-1")String username,
							   @RequestParam(value="password",defaultValue="-1")String password) {
		if(username!="-1" && password!="-1") {
			usuarioServicio.add(new User(username,password));			
			return new Response<>(Response.STATUS_OK,"El usuario ha sido registrado con éxito");
		}
		
		return new Response<>(Response.STATUS_ERROR,"No se han ingresado los parámetros correctamente");
	}
	
	/**
	 * Actualizar datos del usuario
	 * @param id del usuario a actualizar
	 * @param username del usuario
	 * @param password del usuario
	 * @return status si ha sido exitoso o no 
	 * */
	@PutMapping("/usuarios")
	public Response updateUsuario(@RequestParam(value="id",defaultValue="-1")int id,
								  @RequestParam(value="username",defaultValue="-1")String username,
							      @RequestParam(value="password",defaultValue="-1")String password) {
		if(username!="-1" && password!="-1" && id!=-1) {
			usuarioServicio.update(new User(id,username,password));			
			return new Response<>(Response.STATUS_OK);
		}
		
		return new Response<>(Response.STATUS_ERROR,"No se han ingresado los parámetros correctamente");
	}
	
	
	/**
	 * Eliminar un usuario	 
	 * @param id identificador del usuario
	 * @return status si ha sido exitoso o no
	 * */
	@DeleteMapping("/usuarios")
	public Response removeUsuario(@RequestParam(value="id")int id) {		
		try {
			
			usuarioServicio.remove(id);
			return new Response<>(Response.STATUS_OK);
			
		}catch(Exception err) {
			LOG.error(err);
		}
		return new Response<>(Response.STATUS_ERROR,"No has ingresado correctamente el parámetro id");
	}
	
	/**
	 * Login
	 * @param username del usuario
	 * @param password del usuario 
	 * @return token de la sección actual
	 * */
	@GetMapping("/auth")
	public Response login(@RequestParam(value="username",defaultValue="-1")String username,
		      			  @RequestParam(value="password",defaultValue="-1")String password) {
		
		int exists=usuarioServicio.exists(username,password);
		
		if(exists!=0)//existe
			return new Response<>(Response.STATUS_OK,"http://localhost:8080/web/dashboard");
		
		return new Response<>(Response.STATUS_ERROR,"El usuario no existe");
	}
	
	/*Api Clientes*/
	/*
	 * Obtener todos los clientes
	 * @return listado de clientes 
	 * */
	@GetMapping("/clientes")
	public Response getClientes(){				
		return new Response<Cliente>(Response.STATUS_OK,clienteServicio.all());
	}

	
	
	/*
	 * Obtener al cliente por dniORuc
	 * @param rucdni del clente
	 * @return listado de clientes
	 */
	@GetMapping("/clientes/{rucdni}")
	public Response getClientesBy(@PathVariable("rucdni")String rucdni){				
		return new Response<Cliente>(Response.STATUS_OK,clienteServicio.findByRucODni(rucdni));
	}
	
	/**
	 * Registrar un nuevo cliente
	 * @param rucdni Ruc o Dni del cliente
	 * @param nombre nombre del cliente
	 * @param direccionFiscal dirección fiscal del cliente
	 * @param direccionEntrega dirección de entraga del cliente
	 * @param telefono del cliente
	 * @param email del lciente
	 * @return status si ha sido exitoso o no
	 * */
	@PostMapping("/clientes")
	public Response addCliente(@RequestParam(value="rucdni",defaultValue="-1")String rucdni,
							   @RequestParam(value="nombre",defaultValue="-1")String nombre,
							   @RequestParam(value="direccionFiscal",defaultValue="-1")String direccionFiscal,
							   @RequestParam(value="direccionEntrega",defaultValue="-1")String direccionEntrega,
							   @RequestParam(value="telefono",defaultValue="-1")String telefono,
							   @RequestParam(value="email",defaultValue="-1")String email) {
		if(rucdni!="-1" && 
		   nombre!="-1" &&
		   direccionFiscal!="-1" &&
		   direccionEntrega!="-1" &&
		   telefono!="-1") {
			
			clienteServicio.add(new Cliente(rucdni, nombre, direccionFiscal, direccionEntrega, telefono, email));			
			return new Response<>(Response.STATUS_OK,"El cliente ha sido registrado con éxito");
		}
		
		return new Response<>(Response.STATUS_ERROR,"No se han ingresado los parámetros correctamente");
	}
	
	
	/*Api de productos*/
	
	/**
	 * Obtener todos los productos
	 * @return listado de productos
	 * */
	@GetMapping("/productos")
	public Response getProductos() {
		return new Response<Producto>(Response.STATUS_OK,productoServicio.all());		
	}
	
	
	
	/**
	 * registrar producto
	 * @param descripcion del producto
	 * @param precioCompra del producto
	 * @param precioVenta del producto
	 * @param precioPack7 
	 * @param precioPack15
	 * @param stock del producto
	 * @return producto
	 * */
	@PostMapping("/productos")
	public Response addProducto(@RequestParam(value="descripcion",defaultValue="")String descripcion,
								@RequestParam(value="precioCompra",defaultValue="0.0")float precioCompra,
								@RequestParam(value="precioVenta",defaultValue="0.0")float precioVenta,
								@RequestParam(value="precioPack7",defaultValue="0.0")float precioPack7,
								@RequestParam(value="precioPack15",defaultValue="0.0")float precioPack15,
								@RequestParam(value="stock",defaultValue="0")float stock) {
		Producto productoResultante=productoServicio.add(new Producto(descripcion,precioCompra,precioVenta,precioPack7,precioPack15,stock));
		List<Identificador> identificadores=new ArrayList<>();
		identificadores.add(new Identificador(productoResultante.getId()));
		return new Response<Identificador>(Response.STATUS_OK,"El producto se ha agregado con éxito",identificadores);
	}


	/**
	 * modificar producto por id
	 * @param id del producto
	 * @param precioCompra del producto
	 * @param precioVenta del producto
	 * @param precioPack7 
	 * @param precioPack15
	 * @param stock del producto
	 * @return producto
	 * */
	@PutMapping("/productos")
	public Response updateProducto(@RequestParam(value="id",defaultValue="0")int id,
								@RequestParam(value="descripcion",defaultValue="")String descripcion,
								@RequestParam(value="precioCompra",defaultValue="0.0")float precioCompra,
								@RequestParam(value="precioVenta",defaultValue="0.0")float precioVenta,
								@RequestParam(value="precioPack7",defaultValue="0.0")float precioPack7,
								@RequestParam(value="precioPack15",defaultValue="0.0")float precioPack15,
								@RequestParam(value="stock",defaultValue="0")float stock) {
		productoServicio.update(new Producto(id,descripcion,precioCompra,precioVenta,precioPack7,precioPack15,stock));
		return new Response<Producto>(Response.STATUS_OK,"El producto se ha modificado con éxito");
	}


	
	/*Api Proveedores*/
	/*
	 * Obtener todos los clientes
	 * @return listado de clientes 
	 * */
	@GetMapping("/proveedores")
	public Response getProveedores(){
		return new Response<Proveedor>(Response.STATUS_OK,proveedorServicio.all());
	}
	
	
	/**
	 * registrar proveedor
	 * @param razonsocial del proveedor
	 * @param ruc del proveedor
	 * @param email del proveedor 
	 * @param telefono del proveedor	 
	 * @return status si el registro ha sido exitoso o no
	 * */
	@PostMapping("/proveedores")
	public Response addProveedor(@RequestParam(value="razonsocial",defaultValue="")String razonsocial,
								@RequestParam(value="ruc",defaultValue="")String ruc,
								@RequestParam(value="email",defaultValue="")String email,
								@RequestParam(value="telefono",defaultValue="")String telefono) {
		if(ruc.length()==11 && !razonsocial.isEmpty()) {
			proveedorServicio.add(new Proveedor(razonsocial,ruc,email,telefono));
			return new Response<Producto>(Response.STATUS_OK,"El proveedor se ha agregado con éxito");		
		}
		
		return new Response<>(Response.STATUS_ERROR,"No se han ingresado los parámetros correctamente");
	}
	
	/*Api Estados tablas*/
	
	/*
	 * Obtener todos los clientes
	 * @return listado de clientes 
	 * */
	@GetMapping("/estados/{tabla}")
	public Response getEstados(@PathVariable("tabla")String tabla){
		return new Response<EstadoTabla>(Response.STATUS_OK,estadoTablaServicio.findByNombreTabla(tabla));
	}
}
