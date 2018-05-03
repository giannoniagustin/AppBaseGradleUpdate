package transporte.appbase.Servidor;


public class EnviarUsuarioRegistroCommand extends EnviarUsuarioCommand {
    @Override
    public void Ejecutar() {
        super.manejadorInterfaz.mostrarDialogoEspera("Cargando...");
        super.server.enviarUsuarioRegistro(usuario, this);
    }
}
