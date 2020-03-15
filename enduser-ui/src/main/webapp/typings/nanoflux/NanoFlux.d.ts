
declare namespace NF {
    
    class NanoFlux {
        createStore(name: string, store: any);
        createActions(name: string, dispatcher: any, store: any);
        createDispatcher(name: string);
        getStore(name: string);
        getActions(name: string);
    }
    
}
declare module 'NanoFlux' {
    var obj:NF.NanoFlux;

    export = obj;
        
    /*export class Store {
        notify(args?: any)
    }*/
}