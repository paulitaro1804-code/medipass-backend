package com.medipass.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${medipass.mail.from}")
    private String fromAddress;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendPolicyApproved(String toEmail, String patientName, String policyNumber) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom(fromAddress, "MEDIPASS");
            helper.setTo(toEmail);
            helper.setSubject("✅ Tu póliza fue aprobada - MEDIPASS");
            helper.setText(buildApprovedHtml(patientName, policyNumber), true);

            mailSender.send(message);
        } catch (Exception e) {
            // No interrumpir el flujo si el correo falla
            System.err.println("[EmailService] Error enviando correo de aprobación: " + e.getMessage());
        }
    }

    public void sendPolicyRejected(String toEmail, String patientName, String policyNumber, String reason) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom(fromAddress, "MEDIPASS");
            helper.setTo(toEmail);
            helper.setSubject("❌ Tu póliza no fue aprobada - MEDIPASS");
            helper.setText(buildRejectedHtml(patientName, policyNumber, reason), true);

            mailSender.send(message);
        } catch (Exception e) {
            System.err.println("[EmailService] Error enviando correo de rechazo: " + e.getMessage());
        }
    }

    private String buildApprovedHtml(String name, String policy) {
        return """
            <!DOCTYPE html>
            <html lang="es">
            <head><meta charset="UTF-8"></head>
            <body style="margin:0;padding:0;background:#0f172a;font-family:Arial,sans-serif;">
              <table width="100%%" cellpadding="0" cellspacing="0" style="background:#0f172a;padding:40px 20px;">
                <tr><td align="center">
                  <table width="600" cellpadding="0" cellspacing="0" style="background:#1e293b;border-radius:12px;overflow:hidden;border:1px solid #334155;">
                    <!-- Header -->
                    <tr>
                      <td style="background:linear-gradient(135deg,#6366f1,#8b5cf6);padding:32px;text-align:center;">
                        <div style="width:48px;height:48px;background:rgba(255,255,255,0.2);border-radius:10px;display:inline-flex;align-items:center;justify-content:center;margin-bottom:12px;">
                          <span style="color:white;font-size:24px;font-weight:bold;">M</span>
                        </div>
                        <h1 style="color:white;margin:0;font-size:24px;font-weight:700;">MEDIPASS</h1>
                        <p style="color:rgba(255,255,255,0.8);margin:4px 0 0;font-size:13px;">Sistema de Gestión Médica</p>
                      </td>
                    </tr>
                    <!-- Body -->
                    <tr>
                      <td style="padding:36px 40px;">
                        <div style="text-align:center;margin-bottom:28px;">
                          <div style="width:64px;height:64px;background:#16a34a22;border:2px solid #16a34a;border-radius:50%%;display:inline-flex;align-items:center;justify-content:center;margin-bottom:16px;">
                            <span style="font-size:28px;">✅</span>
                          </div>
                          <h2 style="color:#f1f5f9;margin:0 0 8px;font-size:22px;">¡Póliza Aprobada!</h2>
                          <p style="color:#94a3b8;margin:0;font-size:15px;">Tu solicitud ha sido revisada y aprobada</p>
                        </div>
                        <p style="color:#cbd5e1;font-size:15px;line-height:1.6;margin:0 0 20px;">
                          Hola <strong style="color:#f1f5f9;">%s</strong>,
                        </p>
                        <p style="color:#cbd5e1;font-size:15px;line-height:1.6;margin:0 0 24px;">
                          Nos complace informarte que tu póliza de salud ha sido <strong style="color:#4ade80;">validada y aprobada</strong>
                          por nuestro equipo. Ya puedes acceder a todos los servicios de MEDIPASS con tu cuenta.
                        </p>
                        <!-- Policy info box -->
                        <div style="background:#0f172a;border:1px solid #334155;border-radius:8px;padding:20px;margin-bottom:28px;">
                          <p style="color:#64748b;font-size:12px;text-transform:uppercase;letter-spacing:0.05em;margin:0 0 8px;">Número de póliza</p>
                          <p style="color:#a78bfa;font-size:18px;font-weight:700;font-family:monospace;margin:0;">%s</p>
                        </div>
                        <p style="color:#cbd5e1;font-size:15px;line-height:1.6;margin:0 0 28px;">
                          Puedes iniciar sesión en el portal con tu correo electrónico y la contraseña que registraste.
                        </p>
                        <div style="text-align:center;">
                          <a href="http://localhost:8205/auth/login"
                             style="display:inline-block;background:linear-gradient(135deg,#6366f1,#8b5cf6);color:white;text-decoration:none;padding:14px 32px;border-radius:8px;font-weight:600;font-size:15px;">
                            Iniciar Sesión en MEDIPASS
                          </a>
                        </div>
                      </td>
                    </tr>
                    <!-- Footer -->
                    <tr>
                      <td style="background:#0f172a;padding:20px 40px;text-align:center;border-top:1px solid #1e293b;">
                        <p style="color:#475569;font-size:12px;margin:0;">© 2026 MEDIPASS · Sistema de Gestión Médica · Colombia</p>
                      </td>
                    </tr>
                  </table>
                </td></tr>
              </table>
            </body>
            </html>
            """.formatted(name, policy);
    }

    private String buildRejectedHtml(String name, String policy, String reason) {
        return """
            <!DOCTYPE html>
            <html lang="es">
            <head><meta charset="UTF-8"></head>
            <body style="margin:0;padding:0;background:#0f172a;font-family:Arial,sans-serif;">
              <table width="100%%" cellpadding="0" cellspacing="0" style="background:#0f172a;padding:40px 20px;">
                <tr><td align="center">
                  <table width="600" cellpadding="0" cellspacing="0" style="background:#1e293b;border-radius:12px;overflow:hidden;border:1px solid #334155;">
                    <tr>
                      <td style="background:linear-gradient(135deg,#6366f1,#8b5cf6);padding:32px;text-align:center;">
                        <div style="width:48px;height:48px;background:rgba(255,255,255,0.2);border-radius:10px;display:inline-flex;align-items:center;justify-content:center;margin-bottom:12px;">
                          <span style="color:white;font-size:24px;font-weight:bold;">M</span>
                        </div>
                        <h1 style="color:white;margin:0;font-size:24px;font-weight:700;">MEDIPASS</h1>
                        <p style="color:rgba(255,255,255,0.8);margin:4px 0 0;font-size:13px;">Sistema de Gestión Médica</p>
                      </td>
                    </tr>
                    <tr>
                      <td style="padding:36px 40px;">
                        <div style="text-align:center;margin-bottom:28px;">
                          <div style="width:64px;height:64px;background:#dc262622;border:2px solid #dc2626;border-radius:50%%;display:inline-flex;align-items:center;justify-content:center;margin-bottom:16px;">
                            <span style="font-size:28px;">❌</span>
                          </div>
                          <h2 style="color:#f1f5f9;margin:0 0 8px;font-size:22px;">Póliza No Aprobada</h2>
                          <p style="color:#94a3b8;margin:0;font-size:15px;">Tu solicitud requiere atención</p>
                        </div>
                        <p style="color:#cbd5e1;font-size:15px;line-height:1.6;margin:0 0 20px;">
                          Hola <strong style="color:#f1f5f9;">%s</strong>,
                        </p>
                        <p style="color:#cbd5e1;font-size:15px;line-height:1.6;margin:0 0 24px;">
                          Lamentamos informarte que tu póliza <strong style="color:#f87171;font-family:monospace;">%s</strong>
                          no pudo ser aprobada en esta ocasión.
                        </p>
                        <div style="background:#450a0a;border:1px solid #7f1d1d;border-radius:8px;padding:20px;margin-bottom:28px;">
                          <p style="color:#fca5a5;font-size:13px;font-weight:600;margin:0 0 8px;">Motivo del rechazo:</p>
                          <p style="color:#fecaca;font-size:14px;margin:0;line-height:1.5;">%s</p>
                        </div>
                        <p style="color:#cbd5e1;font-size:15px;line-height:1.6;margin:0 0 8px;">
                          Si consideras que hay un error o deseas más información, puedes contactar a nuestro equipo de soporte.
                        </p>
                      </td>
                    </tr>
                    <tr>
                      <td style="background:#0f172a;padding:20px 40px;text-align:center;border-top:1px solid #1e293b;">
                        <p style="color:#475569;font-size:12px;margin:0;">© 2026 MEDIPASS · Sistema de Gestión Médica · Colombia</p>
                      </td>
                    </tr>
                  </table>
                </td></tr>
              </table>
            </body>
            </html>
            """.formatted(name, policy, reason);
    }

    public void sendTicketResponse(String toEmail, String patientName, String subject, String responseMessage) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setFrom(fromAddress, "MEDIPASS Soporte");
            helper.setTo(toEmail);
            helper.setSubject("💬 Respuesta a tu solicitud - MEDIPASS");
            helper.setText(buildTicketResponseHtml(patientName, subject, responseMessage), true);
            mailSender.send(message);
        } catch (Exception e) {
            System.err.println("[EmailService] Error enviando respuesta de ticket: " + e.getMessage());
        }
    }

    public void sendTicketResolved(String toEmail, String patientName, String subject) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setFrom(fromAddress, "MEDIPASS Soporte");
            helper.setTo(toEmail);
            helper.setSubject("✅ Tu solicitud fue resuelta - MEDIPASS");
            helper.setText(buildTicketResolvedHtml(patientName, subject), true);
            mailSender.send(message);
        } catch (Exception e) {
            System.err.println("[EmailService] Error enviando notificación de resolución: " + e.getMessage());
        }
    }

    private String buildTicketResponseHtml(String name, String subject, String responseMsg) {
        return """
            <!DOCTYPE html>
            <html lang="es">
            <head><meta charset="UTF-8"></head>
            <body style="margin:0;padding:0;background:#0f172a;font-family:Arial,sans-serif;">
              <table width="100%%" cellpadding="0" cellspacing="0" style="background:#0f172a;padding:40px 20px;">
                <tr><td align="center">
                  <table width="600" cellpadding="0" cellspacing="0" style="background:#1e293b;border-radius:12px;overflow:hidden;border:1px solid #334155;">
                    <tr>
                      <td style="background:linear-gradient(135deg,#0ea5e9,#0284c7);padding:32px;text-align:center;">
                        <h1 style="color:white;margin:0;font-size:24px;font-weight:700;">MEDIPASS</h1>
                        <p style="color:rgba(255,255,255,0.8);margin:4px 0 0;font-size:13px;">Centro de Soporte</p>
                      </td>
                    </tr>
                    <tr>
                      <td style="padding:36px 40px;">
                        <div style="text-align:center;margin-bottom:24px;">
                          <span style="font-size:36px;">💬</span>
                          <h2 style="color:#f1f5f9;margin:8px 0 4px;font-size:20px;">Respuesta a tu solicitud</h2>
                        </div>
                        <p style="color:#cbd5e1;font-size:15px;line-height:1.6;margin:0 0 16px;">
                          Hola <strong style="color:#f1f5f9;">%s</strong>, hemos respondido a tu solicitud:
                        </p>
                        <div style="background:#0f172a;border:1px solid #334155;border-radius:8px;padding:16px;margin-bottom:20px;">
                          <p style="color:#64748b;font-size:11px;text-transform:uppercase;letter-spacing:0.05em;margin:0 0 6px;">Asunto</p>
                          <p style="color:#e2e8f0;font-size:14px;font-weight:600;margin:0;">%s</p>
                        </div>
                        <div style="background:#0c2340;border:1px solid #0ea5e9;border-left:4px solid #0ea5e9;border-radius:4px;padding:16px;margin-bottom:24px;">
                          <p style="color:#64748b;font-size:11px;text-transform:uppercase;letter-spacing:0.05em;margin:0 0 8px;">Respuesta del equipo de soporte</p>
                          <p style="color:#e2e8f0;font-size:14px;line-height:1.7;margin:0;">%s</p>
                        </div>
                        <div style="text-align:center;">
                          <a href="http://localhost:8205/dashboard/patient/support"
                             style="display:inline-block;background:linear-gradient(135deg,#0ea5e9,#0284c7);color:white;text-decoration:none;padding:12px 28px;border-radius:8px;font-weight:600;font-size:14px;">
                            Ver mis solicitudes
                          </a>
                        </div>
                      </td>
                    </tr>
                    <tr>
                      <td style="background:#0f172a;padding:16px 40px;text-align:center;border-top:1px solid #1e293b;">
                        <p style="color:#475569;font-size:12px;margin:0;">© 2026 MEDIPASS · Sistema de Gestión Médica · Colombia</p>
                      </td>
                    </tr>
                  </table>
                </td></tr>
              </table>
            </body>
            </html>
            """.formatted(name, subject, responseMsg);
    }

    private String buildTicketResolvedHtml(String name, String subject) {
        return """
            <!DOCTYPE html>
            <html lang="es">
            <head><meta charset="UTF-8"></head>
            <body style="margin:0;padding:0;background:#0f172a;font-family:Arial,sans-serif;">
              <table width="100%%" cellpadding="0" cellspacing="0" style="background:#0f172a;padding:40px 20px;">
                <tr><td align="center">
                  <table width="600" cellpadding="0" cellspacing="0" style="background:#1e293b;border-radius:12px;overflow:hidden;border:1px solid #334155;">
                    <tr>
                      <td style="background:linear-gradient(135deg,#10b981,#059669);padding:32px;text-align:center;">
                        <h1 style="color:white;margin:0;font-size:24px;font-weight:700;">MEDIPASS</h1>
                        <p style="color:rgba(255,255,255,0.8);margin:4px 0 0;font-size:13px;">Centro de Soporte</p>
                      </td>
                    </tr>
                    <tr>
                      <td style="padding:36px 40px;">
                        <div style="text-align:center;margin-bottom:24px;">
                          <div style="width:64px;height:64px;background:#10b98122;border:2px solid #10b981;border-radius:50%%;display:inline-flex;align-items:center;justify-content:center;margin-bottom:12px;">
                            <span style="font-size:28px;">✅</span>
                          </div>
                          <h2 style="color:#f1f5f9;margin:0 0 4px;font-size:20px;">¡Solicitud resuelta!</h2>
                          <p style="color:#94a3b8;margin:0;font-size:13px;">Tu caso ha sido atendido y cerrado</p>
                        </div>
                        <p style="color:#cbd5e1;font-size:15px;line-height:1.6;margin:0 0 16px;">
                          Hola <strong style="color:#f1f5f9;">%s</strong>, nos complace informarte que tu solicitud ha sido resuelta satisfactoriamente.
                        </p>
                        <div style="background:#0f172a;border:1px solid #334155;border-radius:8px;padding:16px;margin-bottom:24px;">
                          <p style="color:#64748b;font-size:11px;text-transform:uppercase;letter-spacing:0.05em;margin:0 0 6px;">Solicitud cerrada</p>
                          <p style="color:#e2e8f0;font-size:14px;font-weight:600;margin:0;">%s</p>
                        </div>
                        <p style="color:#94a3b8;font-size:13px;line-height:1.6;margin:0 0 24px;">
                          Si tienes alguna otra consulta o el problema persiste, puedes crear una nueva solicitud desde tu portal.
                        </p>
                        <div style="text-align:center;">
                          <a href="http://localhost:8205/dashboard/patient/support"
                             style="display:inline-block;background:linear-gradient(135deg,#10b981,#059669);color:white;text-decoration:none;padding:12px 28px;border-radius:8px;font-weight:600;font-size:14px;">
                            Ver mis solicitudes
                          </a>
                        </div>
                      </td>
                    </tr>
                    <tr>
                      <td style="background:#0f172a;padding:16px 40px;text-align:center;border-top:1px solid #1e293b;">
                        <p style="color:#475569;font-size:12px;margin:0;">© 2026 MEDIPASS · Sistema de Gestión Médica · Colombia</p>
                      </td>
                    </tr>
                  </table>
                </td></tr>
              </table>
            </body>
            </html>
            """.formatted(name, subject);
    }
}
