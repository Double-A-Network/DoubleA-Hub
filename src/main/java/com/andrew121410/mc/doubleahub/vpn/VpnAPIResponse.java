package com.andrew121410.mc.doubleahub.vpn;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;
import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public class VpnAPIResponse {

    @JsonProperty("ip")
    private String ip;

    @JsonProperty("security")
    private Map<String, Boolean> security;

    @JsonProperty("location")
    private Map<String, String> location;

    @JsonProperty("network")
    private Map<String, String> network;

    //
    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Map<String, Boolean> getSecurity() {
        return security;
    }

    public void setSecurity(Map<String, Boolean> security) {
        this.security = security;
    }

    public Map<String, String> getLocation() {
        return location;
    }

    public void setLocation(Map<String, String> location) {
        this.location = location;
    }

    public Map<String, String> getNetwork() {
        return network;
    }

    public void setNetwork(Map<String, String> network) {
        this.network = network;
    }

    public String toJsonPrettyPrint() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String toString() {
        return "VpnAPIResponse{" +
                "ip='" + ip + '\'' +
                ", security=" + security +
                ", location=" + location +
                ", network=" + network +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VpnAPIResponse that = (VpnAPIResponse) o;
        return Objects.equals(ip, that.ip) && Objects.equals(security, that.security) && Objects.equals(location, that.location) && Objects.equals(network, that.network);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ip, security, location, network);
    }
}
