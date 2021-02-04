package com.example.projetopdmgrupo3.models;

public class InspecaoOnGoing {
    int id;
    Obra obra;
    Cliente cliente;
    int isInspecaoOnGoing;

    public InspecaoOnGoing() {
    }

    public InspecaoOnGoing(Obra obra, Cliente cliente, int isInspecaoOnGoing) {
        this.obra = obra;
        this.cliente = cliente;
        this.isInspecaoOnGoing = isInspecaoOnGoing;
    }

    public InspecaoOnGoing(int id, Obra obra, Cliente cliente, int isInspecaoOnGoing) {
        this.id = id;
        this.obra = obra;
        this.cliente = cliente;
        this.isInspecaoOnGoing = isInspecaoOnGoing;
    }

    public InspecaoOnGoing(int id, int obraId, String obraLocalidade, String obraMorada, String obraCodigoPostal, int obraIdCliente, String obraCreatedAt, int clienteId, String clienteNome, String clienteEmail, String clienteTelefone, String clienteDataNascimento, String clienteCreatedAt, int isInspecaoOnGoing) {
        this.id = id;
        this.obra.setId(id);
        this.obra.setLocalidade(obraLocalidade);
        this.obra.setMorada(obraMorada);
        this.obra.setCodigoPostal(obraCodigoPostal);
        this.obra.setIdCliente(obraIdCliente);
        this.obra.setCreatedAt(obraCreatedAt);
        this.cliente.setId(clienteId);
        this.cliente.setNome(clienteNome);
        this.cliente.setEmail(clienteEmail);
        this.cliente.setTelefone(clienteTelefone);
        this.cliente.setDataNascimento(clienteDataNascimento);
        this.cliente.setCreatedAt(clienteCreatedAt);
    }

    public InspecaoOnGoing(int id, Obra obra, int clienteId, String clienteNome, String clienteEmail, String clienteTelefone, String clienteDataNascimento, String clienteCreatedAt, int isInspecaoOnGoing) {
        this.id = id;
        this.obra = obra;
        this.cliente.setId(clienteId);
        this.cliente.setNome(clienteNome);
        this.cliente.setEmail(clienteEmail);
        this.cliente.setTelefone(clienteTelefone);
        this.cliente.setDataNascimento(clienteDataNascimento);
        this.cliente.setCreatedAt(clienteCreatedAt);
    }

    public InspecaoOnGoing(int id, int obraId, String obraLocalidade, String obraMorada, String obraCodigoPostal, int obraIdCliente, String obraCreatedAt, Cliente cliente, int isInspecaoOnGoing) {
        this.id = id;
        this.obra.setId(id);
        this.obra.setLocalidade(obraLocalidade);
        this.obra.setMorada(obraMorada);
        this.obra.setCodigoPostal(obraCodigoPostal);
        this.obra.setIdCliente(obraIdCliente);
        this.obra.setCreatedAt(obraCreatedAt);
        this.cliente = cliente;
    }

    public InspecaoOnGoing(int obraId, String obraLocalidade, String obraMorada, String obraCodigoPostal, int obraIdCliente, String obraCreatedAt, int clienteId, String clienteNome, String clienteEmail, String clienteTelefone, String clienteDataNascimento, String clienteCreatedAt, int isInspecaoOnGoing) {
        this.obra.setId(id);
        this.obra.setLocalidade(obraLocalidade);
        this.obra.setMorada(obraMorada);
        this.obra.setCodigoPostal(obraCodigoPostal);
        this.obra.setIdCliente(obraIdCliente);
        this.obra.setCreatedAt(obraCreatedAt);
        this.cliente.setId(clienteId);
        this.cliente.setNome(clienteNome);
        this.cliente.setEmail(clienteEmail);
        this.cliente.setTelefone(clienteTelefone);
        this.cliente.setDataNascimento(clienteDataNascimento);
        this.cliente.setCreatedAt(clienteCreatedAt);
    }

    public InspecaoOnGoing(Obra obra, int clienteId, String clienteNome, String clienteEmail, String clienteTelefone, String clienteDataNascimento, String clienteCreatedAt, int isInspecaoOnGoing) {
        this.obra = obra;
        this.cliente.setId(clienteId);
        this.cliente.setNome(clienteNome);
        this.cliente.setEmail(clienteEmail);
        this.cliente.setTelefone(clienteTelefone);
        this.cliente.setDataNascimento(clienteDataNascimento);
        this.cliente.setCreatedAt(clienteCreatedAt);
    }

    public InspecaoOnGoing(int obraId, String obraLocalidade, String obraMorada, String obraCodigoPostal, int obraIdCliente, String obraCreatedAt, Cliente cliente, int isInspecaoOnGoing) {
        this.obra.setId(id);
        this.obra.setLocalidade(obraLocalidade);
        this.obra.setMorada(obraMorada);
        this.obra.setCodigoPostal(obraCodigoPostal);
        this.obra.setIdCliente(obraIdCliente);
        this.obra.setCreatedAt(obraCreatedAt);
        this.cliente = cliente;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Obra getObra() {
        return obra;
    }

    public void setObra(Obra obra) {
        this.obra = obra;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public int getIsInspecaoOnGoing() {
        return isInspecaoOnGoing;
    }

    public void setIsInspecaoOnGoing(int isInspecaoOnGoing) {
        this.isInspecaoOnGoing = isInspecaoOnGoing;
    }

    public void setObraId(int id) {
        this.obra.setId(id);
    }

    public void setObraLocalidade(String localidade) {
        this.obra.setLocalidade(localidade);
    }

    public void setObraMorada(String morada) {
        this.obra.setMorada(morada);
    }

    public void setObraCodigoPostal(String codigoPostal) {
        this.obra.setCodigoPostal(codigoPostal);
    }

    public void setObraIdCliente(int idCliente) {
        this.obra.setIdCliente(idCliente);
    }

    public void setObraCreatedAt(String createdAt) {
        this.obra.setCreatedAt(createdAt);
    }

    public void setClienteId(int id) {
        this.cliente.setId(id);
    }

    public void setClienteNome(String nome) {
        this.cliente.setNome(nome);
    }

    public void setClienteEmail(String email) {
        this.cliente.setEmail(email);
    }

    public void setClienteTelefone(String telefone) {
        this.cliente.setTelefone(telefone);
    }

    public void setClienteDataNascimento(String dataNascimento) {
        this.cliente.setDataNascimento(dataNascimento);
    }

    public void setClienteCreatedAt(String createdAt) {
        this.cliente.setCreatedAt(createdAt);
    }

}
