package transporte.appbase.Servidor;

/**
 * Created by soled_000 on 15/4/2016.
 */
public class EnviarUsuarioRegistradoCommand extends EnviarUsuarioCommand {
    @Override
    public void Ejecutar() {
        super.manejadorInterfaz.mostrarDialogoEspera("Cargando...");
        super.server.enviarUsuarioRegistrado(usuario, this);
    }
}
