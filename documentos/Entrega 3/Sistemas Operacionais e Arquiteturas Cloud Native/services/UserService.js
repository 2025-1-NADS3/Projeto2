const fecapayDB  = require('../db/db.js')

exports.getSaldoUser = async (ra) => {
    const result = await fecapayDB.query(
        `SELECT saldo FROM creditos WHERE ra_aluno = $1`, [ra]
    )
    return result.rows[0]?.saldo
}

exports.atualizarSaldo = async (ra, novoSaldo) => {
    await fecapayDB.query(
        `UPDATE creditos SET saldo = $1 WHERE ra_aluno = $2`, [novoSaldo, ra]
    )
}

