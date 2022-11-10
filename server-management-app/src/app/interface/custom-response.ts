import { Server } from "./server";

export interface CustomResponse {
    timeStamp: Date;
    statusCode: string;
    status: string;
    message: string;
    developerMessage: string;
    data: {
        servers?: Server[], server?: Server
    };
}