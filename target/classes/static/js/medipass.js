/* ===========================================
   MEDIPASS - JavaScript Utilities
   =========================================== */

document.addEventListener('DOMContentLoaded', function () {

  /* ------ 1. Inicializar Lucide Icons ------ */
  if (typeof lucide !== 'undefined') { lucide.createIcons(); }

  /* ------ 2. Header sticky con efecto blur ------ */
  var header = document.getElementById('main-header');
  if (header) {
    window.addEventListener('scroll', function () {
      header.classList.toggle('scrolled', window.scrollY > 50);
    });
  }

  /* ------ 3. Menú móvil (landing) ------ */
  var menuBtn    = document.getElementById('mobile-menu-btn');
  var mobileMenu = document.getElementById('mobile-menu');
  var menuIcon   = document.getElementById('menu-icon');
  var closeIcon  = document.getElementById('close-icon');
  if (menuBtn && mobileMenu) {
    menuBtn.addEventListener('click', function () {
      var hidden = mobileMenu.classList.contains('hidden');
      mobileMenu.classList.toggle('hidden');
      if (menuIcon)  menuIcon.classList.toggle('hidden', !hidden);
      if (closeIcon) closeIcon.classList.toggle('hidden', hidden);
    });
  }

  /* ------ 4. Sidebar dashboard (móvil) ------ */
  var sidebarToggle = document.getElementById('sidebar-toggle');
  var sidebar       = document.getElementById('dashboard-sidebar');
  var overlay       = document.getElementById('sidebar-overlay');
  if (sidebarToggle && sidebar) {
    sidebarToggle.addEventListener('click', function () {
      sidebar.classList.toggle('open');
      if (overlay) overlay.classList.toggle('active');
    });
  }
  if (overlay) {
    overlay.addEventListener('click', function () {
      if (sidebar) sidebar.classList.remove('open');
      overlay.classList.remove('active');
    });
  }

  /* ------ 5. Toggle mostrar/ocultar contraseña ------ */
  document.querySelectorAll('.password-toggle').forEach(function (btn) {
    btn.addEventListener('click', function () {
      var targetId = btn.getAttribute('data-target');
      var input    = document.getElementById(targetId);
      if (!input) return;
      var show = input.type === 'password';
      input.type = show ? 'text' : 'password';
      var icon = btn.querySelector('i[data-lucide]');
      if (icon) {
        icon.setAttribute('data-lucide', show ? 'eye-off' : 'eye');
        if (typeof lucide !== 'undefined') lucide.createIcons();
      }
    });
  });

  /* ------ 6. Animación contadores ------ */
  document.querySelectorAll('.animate-counter').forEach(function (el) {
    var target   = parseInt(el.getAttribute('data-target'), 10);
    var suffix   = el.getAttribute('data-suffix') || '';
    var duration = 2000;
    var started  = false;
    var observer = new IntersectionObserver(function (entries) {
      entries.forEach(function (entry) {
        if (entry.isIntersecting && !started) {
          started = true;
          var startTime = performance.now();
          var tick = function (now) {
            var elapsed  = now - startTime;
            var progress = Math.min(elapsed / duration, 1);
            var easeOut  = 1 - Math.pow(1 - progress, 3);
            el.textContent = Math.floor(target * easeOut).toLocaleString('es-CO') + suffix;
            if (progress < 1) requestAnimationFrame(tick);
            else el.textContent = target.toLocaleString('es-CO') + suffix;
          };
          requestAnimationFrame(tick);
          observer.disconnect();
        }
      });
    }, { threshold: 0.5 });
    observer.observe(el);
  });

  /* ------ 7. Auto-dismiss alertas ------ */
  document.querySelectorAll('.alert-auto-dismiss').forEach(function (el) {
    setTimeout(function () {
      el.style.transition = 'opacity .3s';
      el.style.opacity    = '0';
      setTimeout(function () { el.remove(); }, 300);
    }, 5000);
  });

  /* ------ 8. Confirmación de acciones destructivas ------ */
  document.querySelectorAll('[data-confirm]').forEach(function (btn) {
    btn.addEventListener('click', function (e) {
      var msg = btn.getAttribute('data-confirm') || '¿Estás seguro?';
      if (!confirm(msg)) e.preventDefault();
    });
  });

  /* ------ 9. Formulario con indicador de carga ------ */
  document.querySelectorAll('form[data-loading]').forEach(function (form) {
    form.addEventListener('submit', function () {
      var submitBtn = form.querySelector('[type="submit"]');
      if (submitBtn) {
        submitBtn.disabled = true;
        var loadingText = submitBtn.getAttribute('data-loading-text') || 'Procesando...';
        submitBtn.textContent = loadingText;
      }
    });
  });

  /* ------ 10. Smooth scroll para anclas de la landing ------ */
  document.querySelectorAll('a[href^="#"]').forEach(function (anchor) {
    anchor.addEventListener('click', function (e) {
      var target = document.querySelector(anchor.getAttribute('href'));
      if (target) {
        e.preventDefault();
        target.scrollIntoView({ behavior: 'smooth', block: 'start' });
        // Close mobile menu if open
        if (mobileMenu && !mobileMenu.classList.contains('hidden')) {
          mobileMenu.classList.add('hidden');
          if (menuIcon)  menuIcon.classList.remove('hidden');
          if (closeIcon) closeIcon.classList.add('hidden');
        }
      }
    });
  });

});

