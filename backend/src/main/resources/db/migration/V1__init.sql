CREATE TABLE suggestion (
    id          BIGSERIAL PRIMARY KEY,
    session_key UUID         NOT NULL,
    box_x       INTEGER      NOT NULL,
    box_y       INTEGER      NOT NULL,
    box_width   INTEGER      NOT NULL,
    box_height  INTEGER      NOT NULL,
    confidence  DOUBLE PRECISION NOT NULL,
    status      VARCHAR(20)  NOT NULL CHECK (status IN ('OPEN', 'ACCEPTED', 'REJECTED')),
    decided_by  VARCHAR(100),
    decided_at  TIMESTAMPTZ
);

CREATE INDEX suggestion_session_key_idx ON suggestion (session_key);
