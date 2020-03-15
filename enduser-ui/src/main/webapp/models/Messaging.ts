export enum MessageType {
    SUCCESS, INFO, ERROR, WARNING
}

export interface Message {
    title?: string
    content: string
    type?: MessageType
    isModal?: boolean    
    dismissable?: boolean
    sticky?: boolean
}