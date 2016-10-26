# CorsoSpringBatch
Corso Base Spring Batch
Il progetto è stato utilizzato per un mini-corso di Spring Batch, esso contiene 3 sotto progetti, di seguito una breve descrizione.

## EsempioBatch
Questo esempio contiene un batch composto dai seguenti step<br>

1. Tasklet che si occupa di chiamare il metodo di un DAO attraverso MethodInvokingTaskletAdapter<br>
2. Chunk che si occupa avente un custom reader che crea semplicemente un oggetto, un custom processor che setta la proprietà "nome" dell'oggetto e un custom writer che scrive a video il numero dei record da scrivere<br>
Lo step ha inoltre uno StepListener che si occupa di far vedere come funzionano i metodi beforeStep e afterStep<br>
3. Tasklet custom che si occupa di scrivere un messaggio a video<br>

Il job ha inoltre un listener che scrive a video un riepilogo dell'esecuzione<br>

## ComponentiStandardSpring
Questo esempio contiene alcuni componenti base forniti da Spring<br>

1. jdbcTemplate<br>
2. propertyPlaceholder<br>

## InserimentoAnagrafica
Questo progetto contiene la configurazione base da consegnare ai partecipanti del corso per fare l'esercitazione descritta su GoogleDrive.

## InserimentoAnagraficaSoluzione
Questo progetto contiene la soluzione proposta dai docenti per l'esercitazione sopra indicata.

## SpringBatchExamples
Questo progetto contiene i seguenti esempi utili alla spiegazione:<br>
1. Job semplice avente 3 tasklet che scrivono Hello World a video<br>
2. Job avente una tasklet che fa la copia di un file<br>
3. Job avente una tasklet che fa il sort di un file<br>
4. Job avente una tasklet che fa il sort di un file utilizzando un db in ram per loggare<br>
5. Job avente una tasklet che fa il sort di un file utilizzando un db sqllite per loggare<br>
6. 2 Job sviluppati a chunk aventi un custom reader, un custom processor, un custom writer. Si occupano inoltre di gestire le skip e retry exception<br>
