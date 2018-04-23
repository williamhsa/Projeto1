/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sd.projeto1.main;

import com.sd.projeto1.dao.MapaDao;
import com.sd.projeto1.model.Mapa;
import com.sd.projeto1.model.MapaDTO;
import com.sd.projeto1.util.Utilidades;
import java.io.IOException;
import java.math.BigInteger;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.lang3.SerializationUtils;

/**
 *
 * @author willi
 */
public class ServerThreadFour implements Runnable {

    private DatagramPacket receivedPacket;
    private DatagramSocket serverSocket;
    private MapaDTO mapaDTO;
    private static byte[] in;
    private static Map<BigInteger, String> mapa = new HashMap();

    private MapaDao mapaDAO = new MapaDao();

    public ServerThreadFour(DatagramSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    @Override
    public void run() {
        try {
            in = new byte[1400];

            DatagramPacket receivedPacket = MultiQueue.getProcessamentoFila();

            Mapa maparetorno = new Mapa();
            maparetorno = (Mapa) SerializationUtils.deserialize(receivedPacket.getData());

            MapaDTO mapaDTO = new MapaDTO();
            mapaDTO = tipoOperacao(maparetorno);

            send(receivedPacket, mapaDTO);
        } catch (Exception ex) {
            Logger.getLogger(ServerThreadTwo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void send(DatagramPacket receivePacket, MapaDTO mapaDTO) throws IOException {

        InetAddress IPAddress = receivePacket.getAddress();
        int port = receivePacket.getPort();
        byte mensagem[] = new byte[1400];

        mensagem = SerializationUtils.serialize(mapaDTO);

        DatagramPacket sendPacket = new DatagramPacket(mensagem, mensagem.length, IPAddress, port);

        serverSocket.send(sendPacket);
    }

    public static void salvar(Mapa mapa1) {
        BigInteger chave = new BigInteger(String.valueOf(mapa1.getChave()));

        if (mapa.containsKey(mapa1.getChave())) {
            System.out.println("Mensagem com essa chave já adicionada");
        }

        mapa.put(chave, mapa1.getTexto());
    }

    public static void editar(Mapa mapa1) {
        BigInteger chave = new BigInteger(String.valueOf(mapa1.getChave()));

        if (!mapa.containsKey(mapa1.getChave())) {
            System.out.println("Chave não encontrada");
        }

        mapa.put(chave, mapa1.getTexto());
    }

    public static void excluir(Mapa mapa1) {
        BigInteger chave = new BigInteger(String.valueOf(mapa1.getChave()));

        mapa.remove(chave);
    }

    public static String buscar(Mapa mapa1) {
        BigInteger chave = new BigInteger(String.valueOf(mapa1.getChave()));
        return mapa.get(chave);
    }

    public static void imprimeCRUD(Mapa mapa1) {
        System.out.println("\n===============================");
        System.out.println("Chave: " + mapa1.getChave());
        System.out.println("Texto: " + mapa1.getTexto());
        System.out.println("Tipo de Operaçao: " + Utilidades.retornaTipoOperacao(mapa1.getTipoOperacaoId()));
        System.out.println("Data: " + mapa1.getData());
        System.out.println("Tamanho da fila: " + mapa.size());
        System.out.println("===============================");
    }

    public MapaDTO tipoOperacao(Mapa mapaEntity) throws Exception {

        MapaDTO mapaDTO = new MapaDTO();

        switch (mapaEntity.getTipoOperacaoId()) {
            case 1:
                Mapa mi = mapaDAO.salvar(mapaEntity);
                mapaDTO.setMapa(mi);
                if (mi != null) {
                    salvar(mi);
                    imprimeCRUD(mi);
                    mapaDTO.setMensagem("Inserido com Sucesso!");

                } else {
                    mapaDTO.setMensagem("Erro ao inserir!");
                }
                break;
            case 2:
                Mapa ma = mapaDAO.editar(mapaEntity);
                mapaDTO.setMapa(ma);
                if (ma != null) {
                    editar(ma);
                    imprimeCRUD(ma);
                    mapaDTO.setMensagem("Atualizado com Sucesso!");

                } else {
                    mapaDTO.setMensagem("Erro ao atualizar!");
                }
                break;
            case 3:
                Mapa me = mapaDAO.excluir(mapaEntity.getChave());
                me.setTipoOperacaoId(3);
                mapaDTO.setMapa(me);
                if (me != null) {
                    excluir(me);
                    imprimeCRUD(me);
                    mapaDTO.setMensagem("Excluido com Sucesso!");

                } else {
                    mapaDTO.setMensagem("Excluido ao atualizar!");
                }
                break;
            case 4:
                Mapa mb = mapaDAO.buscarPorId(mapaEntity.getChave());
                mb.setTipoOperacaoId(4);
                mapaDTO.setMapa(mb);
                if (mb != null) {
                    //buscar(ma.getChave());
                    imprimeCRUD(mb);
                    mapaDTO.setMensagem("Recuperado com Sucesso!");

                } else {
                    mapaDTO.setMensagem("Erro ao recuperar!");
                }
                break;
            default:
                mapaDTO.setMapa(null);
                mapaDTO.setMensagem("Opção inválida");

        }

        return mapaDTO;
    }

}
