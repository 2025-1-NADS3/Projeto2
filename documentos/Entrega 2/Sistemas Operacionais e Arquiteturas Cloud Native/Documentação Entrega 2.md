# ENTREGA 2 - Sistemas Operacionais e Arquiteturas Cloud Native

Na segunda entrega da matéria foi implementado o banco de dados para garantir que todas as operações CRUD sejam executadas adequadamente.

## Scripts

**Tabela de usuários:**
```sql
CREATE TABLE IF NOT EXISTS public.usuarios
(
    ra integer NOT NULL,
    nome character varying(100) COLLATE pg_catalog."default" NOT NULL,
    sobrenome character varying(100) COLLATE pg_catalog."default" NOT NULL,
    email character varying(150) COLLATE pg_catalog."default" NOT NULL,
    senha character varying(200) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT usuarios_pkey PRIMARY KEY (ra),
    CONSTRAINT usuarios_email_key UNIQUE (email)
)
```

**Tabela de créditos:**
```sql
CREATE TABLE IF NOT EXISTS public.creditos
(
    ra_aluno integer NOT NULL,
    saldo numeric(15,2) DEFAULT 0.00,
    CONSTRAINT creditos_pkey PRIMARY KEY (ra_aluno),
    CONSTRAINT fk_creditos FOREIGN KEY (ra_aluno)
        REFERENCES public.usuarios (ra) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE CASCADE
)

```

**Tabela para boletos:**

```sql

CREATE TABLE IF NOT EXISTS public.boletos
(
    boleto_id integer NOT NULL DEFAULT nextval('boletos_boleto_id_seq'::regclass),
    ra_aluno integer NOT NULL,
    valor numeric(10,2) NOT NULL,
    codigo integer,
    data_boleto date NOT NULL,
    vencimento date NOT NULL,
    status text COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT boletos_pkey PRIMARY KEY (boleto_id),
    CONSTRAINT fk_boletos_usuarios FOREIGN KEY (ra_aluno)
        REFERENCES public.usuarios (ra) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE CASCADE,
    CONSTRAINT boletos_status_check CHECK (status = ANY (ARRAY['pago'::text, 'vencido'::text, 'pendente'::text]))
)

```
