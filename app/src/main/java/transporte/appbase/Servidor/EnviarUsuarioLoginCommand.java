package transporte.appbase.Servidor;


public class EnviarUsuarioLoginCommand extends EnviarUsuarioCommand{
    @Override
    public void Ejecutar() {
        super.manejadorInterfaz.mostrarDialogoEspera("Cargando...");
        super.server.enviarUsuarioLogin(usuario, this);
    }
}
