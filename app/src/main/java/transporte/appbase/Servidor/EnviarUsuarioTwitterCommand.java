package transporte.appbase.Servidor;


public class EnviarUsuarioTwitterCommand extends EnviarUsuarioCommand {


    @Override
    public void Ejecutar() {
        super.manejadorInterfaz.mostrarDialogoEspera("Cargando...");
        super.server.enviarUsuarioTwitter(usuario, this);
    }
}
